package day7;

public abstract class FSItem {

    protected static Directory root;

    protected String name;

    protected Directory parent;

    public static Directory getRoot() {
        if (root != null) {
            return root;
        }

        root = new Directory("root");
        return root;
    }

    protected FSItem(String name){
        this.name = name;
        this.parent = (Directory) this;
    }

    public FSItem(Directory parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract int getSize();

    public abstract boolean isDir();

    public Directory getParent() {
        return parent;
    }
}
