package br.ifmg.produto1_2026.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError{

    private List<FieldMessage> fieldMessages = new ArrayList<FieldMessage>();

    public List<FieldMessage> getFieldMessages() {
        return fieldMessages;
    }

    public void addFieldMessage(FieldMessage fieldMessage){
        fieldMessages.add(fieldMessage);
    }
}
