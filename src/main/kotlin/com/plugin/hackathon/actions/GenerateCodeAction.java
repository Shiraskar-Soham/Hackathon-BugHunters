package com.plugin.hackathon.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.plugin.hackathon.services.AICopilotService;
import org.jetbrains.annotations.NotNull;

public class GenerateCodeAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get the current editor or project
        var editor = event.getDataContext().getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }

        // Use the service to generate code
        String generatedCode = AICopilotService.getInstance().generateCode(editor.getDocument().getText());

        // Insert the generated code into the editor at the current caret position
        var document = editor.getDocument();
        var caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();
        document.insertString(offset, generatedCode);
    }
}
