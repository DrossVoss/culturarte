package culturarte.util;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.*;
import culturarte.casosuso.Categoria;
import culturarte.casosuso.Propuesta;


import javax.swing.tree.DefaultMutableTreeNode;
/**
 * Selector de categorias con JTree.
 * - Las categorías se almacenan como userObject = Categoria (permite acceder a getId/getNombre/getPadre)
 * - Las propuestas se almacenan como userObject = Propuesta (hojas)
 * - Renderiza categorías como carpetas y propuestas como hojas.
 * - Mantiene métodos compatibles con uso previo (getSelectedItem devuelve "REF:NOMBRE").
 */
public class CategoriaTreeSelector extends JPanel {

    
    private static javax.swing.tree.DefaultMutableTreeNode buildCategoriasRoot() {
        javax.swing.tree.DefaultMutableTreeNode root = new javax.swing.tree.DefaultMutableTreeNode("Categorías");

        // Teatro (carpeta)
        javax.swing.tree.DefaultMutableTreeNode teatro = new javax.swing.tree.DefaultMutableTreeNode("Teatro");
        javax.swing.tree.DefaultMutableTreeNode teatroD = new javax.swing.tree.DefaultMutableTreeNode("Teatro Dramático"); teatroD.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode teatroM = new javax.swing.tree.DefaultMutableTreeNode("Teatro Musical"); teatroM.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode comedia = new javax.swing.tree.DefaultMutableTreeNode("Comedia");
        javax.swing.tree.DefaultMutableTreeNode standup = new javax.swing.tree.DefaultMutableTreeNode("Stand-up"); standup.setAllowsChildren(false);
        comedia.add(standup);
        teatro.add(teatroD);
        teatro.add(teatroM);
        teatro.add(comedia);

        // Literatura (carpeta vacía)
        javax.swing.tree.DefaultMutableTreeNode literatura = new javax.swing.tree.DefaultMutableTreeNode("Literatura");

        // Música
        javax.swing.tree.DefaultMutableTreeNode musica = new javax.swing.tree.DefaultMutableTreeNode("Música");
        javax.swing.tree.DefaultMutableTreeNode festival = new javax.swing.tree.DefaultMutableTreeNode("Festival"); festival.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode concierto = new javax.swing.tree.DefaultMutableTreeNode("Concierto"); concierto.setAllowsChildren(false);
        musica.add(festival);
        musica.add(concierto);

        // Cine
        javax.swing.tree.DefaultMutableTreeNode cine = new javax.swing.tree.DefaultMutableTreeNode("Cine");
        javax.swing.tree.DefaultMutableTreeNode cineAl = new javax.swing.tree.DefaultMutableTreeNode("Cine al Aire Libre"); cineAl.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode cinePedal = new javax.swing.tree.DefaultMutableTreeNode("Cine a Pedal"); cinePedal.setAllowsChildren(false);
        cine.add(cineAl);
        cine.add(cinePedal);

        // Danza
        javax.swing.tree.DefaultMutableTreeNode danza = new javax.swing.tree.DefaultMutableTreeNode("Danza");
        javax.swing.tree.DefaultMutableTreeNode ballet = new javax.swing.tree.DefaultMutableTreeNode("Ballet"); ballet.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode flamenco = new javax.swing.tree.DefaultMutableTreeNode("Flamenco"); flamenco.setAllowsChildren(false);
        danza.add(ballet);
        danza.add(flamenco);

        // Carnaval
        javax.swing.tree.DefaultMutableTreeNode carnaval = new javax.swing.tree.DefaultMutableTreeNode("Carnaval");
        javax.swing.tree.DefaultMutableTreeNode murga = new javax.swing.tree.DefaultMutableTreeNode("Murga"); murga.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode humoristas = new javax.swing.tree.DefaultMutableTreeNode("Humoristas"); humoristas.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode parodistas = new javax.swing.tree.DefaultMutableTreeNode("Parodistas"); parodistas.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode lubolos = new javax.swing.tree.DefaultMutableTreeNode("Lubolos"); lubolos.setAllowsChildren(false);
        javax.swing.tree.DefaultMutableTreeNode revista = new javax.swing.tree.DefaultMutableTreeNode("Revista"); revista.setAllowsChildren(false);
        carnaval.add(murga);
        carnaval.add(humoristas);
        carnaval.add(parodistas);
        carnaval.add(lubolos);
        carnaval.add(revista);

        // add top-level folders to root
        root.add(teatro);
        root.add(literatura);
        root.add(musica);
        root.add(cine);
        root.add(danza);
        root.add(carnaval);

        return root;
    }



    private JTextField display;
    private JButton btn;
    private JTree tree;
    private JPopupMenu popup;
    private DefaultMutableTreeNode root;
    private Map<String, DefaultMutableTreeNode> nodesById = new HashMap<>();
    private List<ActionListener> listeners = new ArrayList<>();
    private String selectedValue = null; // "ID:Nombre"

    public CategoriaTreeSelector() {
        super(new BorderLayout());
        display = new JTextField();
        display.setEditable(false);
        btn = new JButton("▾");
        this.add(display, BorderLayout.CENTER);
        this.add(btn, BorderLayout.EAST);

        root = buildCategoriasRoot();
        tree = new JTree(root);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.setToggleClickCount(1);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Renderer: mostrar nombre según tipo (Categoria -> nombre, Propuesta -> titulo)
        DefaultTreeCellRenderer rdr = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (!(value instanceof DefaultMutableTreeNode)) return this;
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                Object uo = node.getUserObject();

                // Decide icon based on whether node has children (treat as folder) or is leaf.
                boolean isLeafNode = (node.getChildCount() == 0);

                if (uo instanceof Categoria) {
                    Categoria c = (Categoria) uo;
                    setText(c.getNombre());
                    // show leaf icon when node has no children; otherwise show folder icon
                    if (isLeafNode) {
                        setIcon(getLeafIcon());
                    } else {
                        setIcon(expanded ? getOpenIcon() : getClosedIcon());
                    }
                } else if (uo instanceof Propuesta) {
                    Propuesta p = (Propuesta) uo;
                    setText(p.getTitulo() != null ? p.getTitulo() : p.getId());
                    // propuestas siempre son hojas
                    setIcon(getLeafIcon());
                } else {
                    setText(uo == null ? "" : uo.toString());
                    // generic node: if it has children show folder icon, else leaf
                    if (isLeafNode) {
                        setIcon(getLeafIcon());
                    } else {
                        setIcon(expanded ? getOpenIcon() : getClosedIcon());
                    }
                }
                return this;
            }
        };tree.setCellRenderer(rdr);

        popup = new JPopupMenu();
        popup.setLayout(new BorderLayout());
        popup.add(new JScrollPane(tree), BorderLayout.CENTER);
        popup.setPreferredSize(new Dimension(320,360));

        ActionListener show = e -> {
            collapseAll();
            popup.show(this, 0, this.getHeight());
        };
        btn.addActionListener(show);
        display.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { show.actionPerformed(null); }
        });

        tree.addTreeSelectionListener(ev -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            Object uo = node.getUserObject();
            if (uo instanceof Categoria) {
                Categoria c = (Categoria) uo;
                selectedValue = c.getId() + ":" + c.getNombre();
                display.setText(c.getNombre());
            } else if (uo instanceof Propuesta) {
                Propuesta p = (Propuesta) uo;
                selectedValue = p.getId() + ":" + (p.getTitulo() != null ? p.getTitulo() : p.getId());
                display.setText(p.getTitulo() != null ? p.getTitulo() : p.getId());
            } else {
                selectedValue = uo == null ? null : uo.toString();
                display.setText(selectedValue == null ? "" : selectedValue);
            }
            popup.setVisible(false);
            ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "selection");
            for (ActionListener l : listeners) {
                l.actionPerformed(ae);
            }
        });
    }

    public void removeAllItems() {
        root.removeAllChildren();
        nodesById.clear();
        ((DefaultTreeModel)tree.getModel()).reload();
        selectedValue = null;
        display.setText("");
    }

    /**
     * Pobla las categorias en formato jerárquico usando Categoria.getPadre() (objeto Categoria o null)
     */
    public void setCategories(java.util.List<Categoria> cats) {
        // Start from template root so folders/subfolders exist
        this.root = buildCategoriasRoot();
        tree.setModel(new DefaultTreeModel(root));

        if (cats == null) {
            ((DefaultTreeModel)tree.getModel()).reload();
            collapseAll();
            return;
        }

        Map<String, DefaultMutableTreeNode> map = new HashMap<>();

        // Known mapping for categories that should appear under specific template folders
        Map<String, String> knownParent = new HashMap<>();
        knownParent.put("Murga", "Carnaval");
        knownParent.put("Humoristas", "Carnaval");
        knownParent.put("Parodistas", "Carnaval");
        knownParent.put("Lubolos", "Carnaval");
        knownParent.put("Revista", "Carnaval");

        knownParent.put("Ballet", "Danza");
        knownParent.put("Flamenco", "Danza");

        knownParent.put("Cine al Aire Libre", "Cine");
        knownParent.put("Cine a Pedal", "Cine");

        knownParent.put("Concierto", "Música");
        knownParent.put("Festival", "Música");

        knownParent.put("Stand-up", "Comedia");

        knownParent.put("Teatro Musical", "Teatro");
        knownParent.put("Teatro Dramático", "Teatro");

        // helper to find a node by its displayed name in the template
        java.util.function.Function<String, DefaultMutableTreeNode> findByName = (name) -> {
            Enumeration<?> en = root.breadthFirstEnumeration();
            while (en.hasMoreElements()) {
                Object o = en.nextElement();
                if (o instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode dm = (DefaultMutableTreeNode) o;
                    Object uo = dm.getUserObject();
                    String nm = uo == null ? "" : uo.toString();
                    if (name.equals(nm)) return dm;
                }
            }
            return null;
        };

        // Create a node entry for each Categoria. If the Categoria's name matches a template folder
        // (e.g. "Teatro", "Carnaval") replace that template node's userObject so it shows the real Categoria.
        java.util.Set<String> templateNames = new java.util.HashSet<>(java.util.Arrays.asList("Teatro","Literatura","Música","Cine","Danza","Carnaval","Comedia"));
        for (Categoria c : cats) {
            DefaultMutableTreeNode templateNode = findByName.apply(c.getNombre());
            if (templateNode != null && templateNames.contains(c.getNombre())) {
                templateNode.setUserObject(c);
                templateNode.setAllowsChildren(true);
                map.put(c.getId(), templateNode);
            } else {
                DefaultMutableTreeNode n = new DefaultMutableTreeNode(c, false);
                n.setAllowsChildren(false);
                map.put(c.getId(), n);
            }
        }

        // Now assemble hierarchy: prefer explicit padre relationship, otherwise use knownParent mapping,
        // otherwise attach to root.
        for (Categoria c : cats) {
            DefaultMutableTreeNode node = map.get(c.getId());
            if (node == null) continue;
            Categoria padreObj = c.getPadre();
            String padreId = padreObj == null ? null : padreObj.getId();
            if (padreId != null && map.containsKey(padreId)) {
                map.get(padreId).add(node);
            } else {
                String pName = knownParent.get(c.getNombre());
                DefaultMutableTreeNode parentNode = null;
                if (pName != null) parentNode = findByName.apply(pName);
                if (parentNode != null) {
                    parentNode.add(node);
                } else {
                    // if node is already part of template (replaced), its parent may be set; otherwise attach to 'Otras' template node
                    /* Unattached node: no 'Otras' folder — omit node from tree to keep root clean. */
                }
            }
        }

        nodesById = map;
        ((DefaultTreeModel)tree.getModel()).reload();
        collapseAll();
    }
public void setPropuestas(java.util.List<Propuesta> propuestas) {
        if (propuestas == null) {
            ((DefaultTreeModel)tree.getModel()).reload();
            collapseAll();
            return;
        }
        // eliminar hojas previas de propuestas (mantener solo nodos de categoria y subcategorias)
        // Recorremos todos los nodos de categorias y retiramos hijos que sean Propuesta
        for (Object _tn : java.util.Collections.list(root.children())) {
            DefaultMutableTreeNode catNode = (DefaultMutableTreeNode) _tn;
            removeProposalLeavesRecursively(catNode);
        }
        // también asegurar que si había hojas en root (propuestas sin categoría) las removemos
        removeProposalLeavesRecursively(root);

        // añadir propuestas
        for (Propuesta p : propuestas) {
            String catId = null;
            if (p.getCategoria() != null) catId = p.getCategoria().getId();
            DefaultMutableTreeNode parent = (catId != null && nodesById.containsKey(catId)) ? nodesById.get(catId) : root;
            DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(p, false); // leaf
            parent.add(leaf);
        }
        ((DefaultTreeModel)tree.getModel()).reload();
        collapseAll();
    }

    // Recursivo: remove children that are Propuesta instances
    private void removeProposalLeavesRecursively(DefaultMutableTreeNode node) {
        java.util.List<DefaultMutableTreeNode> toRemove = new java.util.ArrayList<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
            Object uo = child.getUserObject();
            if (uo instanceof Propuesta) {
                toRemove.add(child);
            } else {
                // recursivamente limpiar subcategorias
                removeProposalLeavesRecursively(child);
            }
        }
        for (DefaultMutableTreeNode rem : toRemove) node.remove(rem);
    }

    public Object getSelectedItem() {
        return selectedValue;
    }

    public void setSelectedItem(Object o) {
        if (o == null) {
            selectedValue = null; display.setText(""); return;
        }
        selectedValue = o.toString();
        if (selectedValue.contains(":")) {
            display.setText(selectedValue.split(":",2)[1]);
        } else {
            display.setText(selectedValue);
        }
        // intentar seleccionar el nodo correspondiente en el arbol
        String id = selectedValue.contains(":") ? selectedValue.split(":",2)[0] : selectedValue;
        if (id != null && nodesById.containsKey(id)) {
            DefaultMutableTreeNode node = nodesById.get(id);
            TreePath tp = new TreePath(node.getPath());
            tree.setSelectionPath(tp);
            tree.scrollPathToVisible(tp);
        }
    }

    public void addActionListener(ActionListener l) {
        listeners.add(l);
    }

    // compatibilidad con DefaultComboBoxModel<String>
    public void setModel(ComboBoxModel<String> model) {
        if (model == null) return;
        java.util.List<String> items = new java.util.ArrayList<>();
        for (int i=0;i<model.getSize();i++) items.add(model.getElementAt(i));
        // agregar como nodos simples bajo root (mantener compatibilidad mínima)
        removeAllItems();
        for (String s : items) {
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(s, false);
            root.add(n);
        }
        ((DefaultTreeModel)tree.getModel()).reload();
        collapseAll();
    }

    // compatibilidad adicional con JComboBox
    public int getItemCount() {
        return root.getChildCount();
    }

    public void setSelectedIndex(int idx) {
        if (idx < 0 || idx >= root.getChildCount()) return;
        DefaultMutableTreeNode n = (DefaultMutableTreeNode) root.getChildAt(idx);
        if (n == null) return;
        tree.setSelectionPath(new TreePath(n.getPath()));
        tree.scrollPathToVisible(new TreePath(n.getPath()));
    }

    // colapsar todo el arbol
    public void collapseAll() {
        for (int i = tree.getRowCount() - 1; i >= 0; i--) tree.collapseRow(i);
    }

    @Override public void setPreferredSize(Dimension d) { super.setPreferredSize(d); display.setPreferredSize(d); }
}
