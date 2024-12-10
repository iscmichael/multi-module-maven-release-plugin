package io.github.iscmichael.mavenplugins.release;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface DiffDetector {
    boolean hasChangedSince(String modulePath, List<String> childModules, Collection<AnnotatedTag> tags) throws IOException;
}
