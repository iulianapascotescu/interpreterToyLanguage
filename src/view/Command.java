package view;

import exceptions.MyException;

abstract class Command {
    private String key, description;
    Command(String key, String description) { this.key = key; this.description = description;}
    abstract void execute() throws MyException;
    String getKey(){return key;}
    String getDescription(){return description;}
}
