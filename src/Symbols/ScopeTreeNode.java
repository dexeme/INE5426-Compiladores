package Symbols;

import java.util.ArrayList;
import java.util.List;

public class ScopeTreeNode {
    private final SymbolTable table;
    private final ScopeTreeNode parent;
    private final ArrayList<ScopeTreeNode> children = new ArrayList<>();

    public ScopeTreeNode(ScopeTreeNode parent) {
        this.table = new SymbolTable();
        this.parent = parent;
    }

    public SymbolTable getTable() { return table; }
    public ScopeTreeNode getParent() { return parent; }
    public List<ScopeTreeNode> getChildren() { return children; }

    public ScopeTreeNode addChild() {
        ScopeTreeNode child = new ScopeTreeNode(this);
        children.add(child);
        return child;
    }
} 