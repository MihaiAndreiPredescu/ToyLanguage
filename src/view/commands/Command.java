package view.commands;

public abstract class Command {
    private String key, description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public abstract void execute();

    public String getDescription() {
        return this.description;
    }
}
