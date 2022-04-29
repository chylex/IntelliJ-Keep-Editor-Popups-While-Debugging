When debugging, IntelliJ-based IDEs stop showing editor popups, such as Quick Documentation and Error Description, to avoid conflicts with debugger popups.
This behavior can be annoying since it affects popups from all elements, and not just those that actually conflict.

This plugin will keep showing all editor popups, even while debugging.
If you hover an element that has popups from both the editor and the debugger, both popups are shown and moving the cursor on top of one hides the other.

[IDEA-120435](https://youtrack.jetbrains.com/issue/IDEA-120435)
