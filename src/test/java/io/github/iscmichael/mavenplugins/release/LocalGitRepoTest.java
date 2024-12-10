package io.github.iscmichael.mavenplugins.release;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.Test;
import scaffolding.TestProject;

import java.util.ArrayList;
import java.util.List;

import static io.github.iscmichael.mavenplugins.release.GitHelper.scmUrlToRemote;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static scaffolding.TestProject.dirToGitScmReference;

public class LocalGitRepoTest {

    TestProject project = TestProject.singleModuleProject();

    @Test
    public void canDetectLocalTags() throws GitAPIException {
        LocalGitRepo repo = new LocalGitRepo(project.local, new LocalTagFetcher(project.local),
                                             new LocalTagPusher(project.local));
        tag(project.local, "some-tag");
        assertThat(repo.hasLocalTag("some-tag"), is(true));
        assertThat(repo.hasLocalTag("some-ta"), is(false));
        assertThat(repo.hasLocalTag("some-tagyo"), is(false));
    }

    @Test
    public void canDetectRemoteTags() throws Exception {
        LocalGitRepo repo = new LocalGitRepo(project.local, new RemoteTagFetcher(project.local, null, null),
                                             new LocalTagPusher(project.local));
        tag(project.origin, "some-tag");
        assertThat(repo.tagsFrom(tags("blah", "some-tag")), equalTo(asList("some-tag")));
        assertThat(repo.tagsFrom(tags("blah", "some-taggart")), equalTo(emptyList()));
    }

    @Test
    public void usesThePassedInScmUrlToFindRemote() throws Exception {
        String remote = scmUrlToRemote(dirToGitScmReference(project.originDir));
        LocalGitRepo repo = new LocalGitRepo(project.local, new RemoteTagFetcher(project.local, remote, null),
                                             new LocalTagPusher(project.local));
        tag(project.origin, "some-tag");

        StoredConfig config = project.local.getRepository().getConfig();
        config.unsetSection("remote", "origin");
        config.save();

        assertThat(repo.tagsFrom(tags("blah", "some-tag")), equalTo(asList("some-tag")));
    }

    @Test
    public void canHaveManyTags() throws GitAPIException {
        int numberOfTags = 50; // setting this to 1000 works but takes too long
        for (int i = 0; i < numberOfTags; i++) {
            tag(project.local, "this-is-a-tag-" + i);
        }
        project.local.push().setPushTags().call();
        LocalGitRepo repo = new LocalGitRepo(project.local, new LocalTagFetcher(project.local),
                                             new LocalTagPusher(project.local));
        for (int i = 0; i < numberOfTags; i++) {
            String tagName = "this-is-a-tag-" + i;
            assertThat(repo.hasLocalTag(tagName), is(true));
            assertThat(repo.tagsFrom(tags(tagName)).size(), is(1));
        }
    }

    private static List<AnnotatedTag> tags(String... tagNames) {
        List<AnnotatedTag> tags = new ArrayList<AnnotatedTag>();
        for (String tagName : tagNames) {
            tags.add(AnnotatedTag.create(tagName, "1", 0));
        }
        return tags;
    }
    private static List<String> emptyList() {
        return new ArrayList<String>();
    }

    private static void tag(Git repo, String name) throws GitAPIException {
        repo.tag().setAnnotated(true).setName(name).setMessage("Some message").call();
    }
}
