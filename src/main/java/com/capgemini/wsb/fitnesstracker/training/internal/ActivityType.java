package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enum representing the type of activity.
 * <p>
 *     The enum contains the following values:
 *     <ul>
 *         <li>RUNNING</li>
 *         <li>CYCLING</li>
 *         <li>WALKING</li>
 *         <li>SWIMMING</li>
 *         <li>TENNIS</li>
 *     </ul>
 *     The enum is used to categorize the activities.
 */
public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tenis");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
