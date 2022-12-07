package day7;

import java.util.ArrayList;
import java.util.List;

public class Directory extends FSItem {

    protected List<FSItem> children = new ArrayList<>();

    protected Directory(String name){
        super(name);
    }

    public Directory(Directory parent, String name) {
        super(parent, name);
    }

    public FSItem addChild(FSItem child) {
        children.add(child);
        return child;
    }

    public FSItem getChild(String name) throws IllegalArgumentException {
        for (FSItem child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }

        throw new IllegalArgumentException("File not found.");
    }

    public List<FSItem> children() {
        return children;
    }

    @Override
    public int getSize() {
        int sum = 0;
        for (FSItem child : children) {
            sum += child.getSize();
        }

        return sum;
    }

    @Override
    public boolean isDir() {
        return true;
    }
}
