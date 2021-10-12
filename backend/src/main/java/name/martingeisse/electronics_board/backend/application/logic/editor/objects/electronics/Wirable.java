package name.martingeisse.electronics_board.backend.application.logic.editor.objects.electronics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;

public interface Wirable {

    @JsonIgnore
    ImmutableSet<String> getPortIds();

}
