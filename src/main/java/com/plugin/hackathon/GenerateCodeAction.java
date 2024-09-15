package com.plugin.hackathon;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class GenerateCodeAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Editor editor = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
        if (editor == null) {
            return;
        }

        Document document = editor.getDocument();
        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText == null || selectedText.isEmpty()) {
            Messages.showErrorDialog("No code selected. Please select code before generating suggestions.", "Error");
            return;
        }

        String generatedCode = com.plugin.hackathon.AICopilotService.getInstance().generateCode(selectedText);

        WriteCommandAction.runWriteCommandAction(event.getProject(), () -> {
            document.insertString(editor.getCaretModel().getOffset(), generatedCode);
        });
    }
}
