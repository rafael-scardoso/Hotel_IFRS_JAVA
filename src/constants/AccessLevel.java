package constants;

import java.util.List;

public enum AccessLevel {
	

    RECEPTIONIST(List.of(
        "REGISTER_GUEST",
        "MAKE_RESERVATION",
        "PERFORM_CHECKIN",
        "PERFORM_CHECKOUT"
    )),

    MANAGER(List.of(
        "REGISTER_GUEST",
        "MAKE_RESERVATION",
        "MANAGE_ROOMS",
        "MANAGE_EMPLOYEES",
        "GENERATE_REPORTS"
    )),

    ADMINISTRATOR(List.of(
        "REGISTER_GUEST",
        "MAKE_RESERVATION",
        "MANAGE_ROOMS",
        "MANAGE_EMPLOYEES",
        "GENERATE_REPORTS",
        "MANAGE_HOTELS"
    ));

    private final List<String> permissions;

    AccessLevel(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }
	
	

}
