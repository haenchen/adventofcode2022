package day7;

public class File extends FSItem {

    protected int size;

    public File(Directory parent, String name, int size) {
        super(parent, name);
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isDir() {
        return false;
    }

    public String getName() {
        return name;
    }
}
