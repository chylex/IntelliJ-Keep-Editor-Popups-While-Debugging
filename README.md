When debugging, IntelliJ-based IDEs stop showing editor tooltips, such as Quick Documentation and Error Description, to avoid conflicts with debugger tooltips.
This plugin will keep showing editor tooltips, even while debugging.

If you hover an element that has tooltips from both the editor and the debugger, both tooltips are shown and moving the cursor on top of one hides the other.

Installing the plugin automatically re-enables all editor tooltips, even if installed in the middle of a debugging session.
Disabling or uninstalling the plugin requires an IDE restart. 

[IDEA-120435](https://youtrack.jetbrains.com/issue/IDEA-120435)
