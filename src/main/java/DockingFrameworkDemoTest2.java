import com.jidesoft.docking.*;
import com.jidesoft.icons.JideIconsFactory;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.JideTabbedPane;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DockingFrameworkDemoTest2 extends DefaultDockableHolder {
    private static DockingFrameworkDemoTest2 _frame;
    private static WindowAdapter _windowListener;


    private static final String PROFILE_NAME = "jidesoft";

    public DockingFrameworkDemoTest2(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
                showDemo(true);
            }
        });

    }


    public static DefaultDockableHolder showDemo(final boolean exit) {
        _frame = new DockingFrameworkDemoTest2("Demo of JIDE Docking Framework");
        _frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _frame.getDockingManager().setXmlFormat(true);
        _frame.setIconImage(JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32).getImage());

        // add a window listener to do clear up when windows closing.
        _windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                clearUp();
                if (exit) {
                    System.exit(0);
                }
            }
        };
        _frame.addWindowListener(_windowListener);

        // Set the profile key
        _frame.getDockingManager().setProfileKey(PROFILE_NAME);

// comment this if you don't want to use javax pref
//        _frame.getDockingManager().setUsePref(false);

        // Uses light-weight outline. There are several options here.
        _frame.getDockingManager().setOutlineMode(DockingManager.FULL_OUTLINE_MODE);

// uncomment following lines to adjust the sliding speed of autohide frame
//        _frame.getDockingManager().setInitDelay(100);
//        _frame.getDockingManager().setSteps(1);
//        _frame.getDockingManager().setStepDelay(0);

// uncomment following lines if you want to customize the tabbed pane used in Docking Framework
//        _frame.getDockingManager().setTabbedPaneCustomizer(new DockingManager.TabbedPaneCustomizer(){
//            public void customize(JideTabbedPane tabbedPane) {
//                tabbedPane.setShowCloseButton(true);
//                tabbedPane.setShowCloseButtonOnTab(true);
//            }
//        });

// uncomment following lines if you want to customize the popup menu of DockableFrame
//       _frame.getDockingManager().setPopupMenuCustomizer(new com.jidesoft.docking.PopupMenuCustomizer() {
//           public void customizePopupMenu(JPopupMenu menu, DockingManager dockingManager, DockableFrame dockableFrame, boolean onTab) {
//              // ... do customization here
//           }
//
//           public boolean isPopupMenuShown(DockingManager dockingManager, DockableFrame dockableFrame, boolean onTab) {
//               return false;
//           }
//       });

        // add menu bar
        _frame.setJMenuBar(createMenuBar());

        // Sets the number of steps you allow user to undo.
        _frame.getDockingManager().setUndoLimit(10);
      /*  _frame.getDockingManager().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                refreshUndoRedoMenuItems();
            }
        });*/

        // Now let's start to addFrame()
        _frame.getDockingManager().beginLoadLayoutData();

        _frame.getDockingManager().setInitSplitPriority(DockingManager.SPLIT_EAST_WEST_SOUTH_NORTH);

        // add all dockable frames
        _frame.getDockingManager().addFrame(createSampleTaskListFrame());
         _frame.getDockingManager().addFrame(createSampleResourceViewFrame());
        _frame.getDockingManager().addFrame(createSampleClassViewFrame());
        _frame.getDockingManager().addFrame(createSampleProjectViewFrame());
        _frame.getDockingManager().addFrame(createSampleServerFrame());
        _frame.getDockingManager().addFrame(createSamplePropertyFrame());
        _frame.getDockingManager().addFrame(createSampleFindResult1Frame());
        _frame.getDockingManager().addFrame(createSampleFindResult2Frame());
        _frame.getDockingManager().addFrame(createSampleFindResult3Frame());
        _frame.getDockingManager().addFrame(createSampleOutputFrame());
        _frame.getDockingManager().addFrame(createSampleCommandFrame());

// just use default size. If you want to overwrite, you can call this method
//        _frame.getDockingManager().setInitBounds(new Rectangle(0, 0, 960, 800));

// disallow drop dockable frame to workspace area
        _frame.getDockingManager().getWorkspace().setAcceptDockableFrame(false);
        _frame.getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);

        // load layout information from previous session. This indicates the end of beginLoadLayoutData() method above.
        _frame.getDockingManager().loadLayoutData();

// uncomment following line(s) if you want to limit certain feature.
// If you uncomment all four lines, then the dockable frame will not
// be moved anywhere.
//        _frame.getDockingManager().setRearrangable(false);
//        _frame.getDockingManager().setAutohidable(false);
//        _frame.getDockingManager().setFloatable(false);
//        _frame.getDockingManager().setHidable(false);

        _frame.toFront();
        return _frame;
    }



    protected static DockableFrame createDockableFrame(String key, Icon icon) {
        DockableFrame frame = new DockableFrame(key, icon);
        frame.setPreferredSize(new Dimension(200, 200));
        return frame;
    }

    protected static DockableFrame createSampleProjectViewFrame() {
        DockableFrame frame = createDockableFrame("Project View", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME1));
        frame.getContext().setInitMode(DockContext.STATE_AUTOHIDE);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
        frame.add(createScrollPane(new JTextArea()));
        return frame;
    }

    protected static DockableFrame createSampleClassViewFrame() {
        DockableFrame frame = createDockableFrame("Class View", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME2));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
        frame.getContext().setInitIndex(1);
        frame.add(createScrollPane(new JTextArea()));
        frame.setTitle("Class View - DockingFrameworkDemo");
        frame.setTabTitle("Class View");
        return frame;
    }

    protected static DockableFrame createSampleServerFrame() {
        DockableFrame frame = createDockableFrame("Server Explorer", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME3));
        frame.getContext().setInitMode(DockContext.STATE_AUTOHIDE);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
        frame.getContext().setInitIndex(0);
        frame.add(createScrollPane(new JTextArea()));
        frame.setToolTipText("Server");
        return frame;
    }

    protected static DockableFrame createSampleResourceViewFrame() {
        DockableFrame frame = createDockableFrame("Resource View", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME4));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_EAST);
        frame.getContext().setInitIndex(1);
        frame.add(createScrollPane(new JTextArea()));
        frame.setTitle("Resource View");
        return frame;
    }

    protected static DockableFrame createSamplePropertyFrame() {
        DockableFrame frame = createDockableFrame("Property", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME5));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_WEST);
        frame.getContext().setInitIndex(0);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.add(createScrollPane(new JTextArea()));
        return frame;
    }

    static int i = 0;

    protected static DockableFrame createSampleTaskListFrame() {
        DockableFrame frame = createDockableFrame("Task List", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME6));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
        JList list = new JList(new String[]{"Task1", "Task2", "Task3"});
        list.setToolTipText("This is a tooltip");
        frame.add(createScrollPane(list));
//        frame.addAdditionalButtonActions(new AbstractAction("test") {
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Test additional buttons.");
//            }
//        }, JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME6));
        return frame;
    }

    protected static DockableFrame createSampleCommandFrame() {
        DockableFrame frame = createDockableFrame("Command", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME7));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
        frame.getContext().setInitIndex(1);
        JTextArea textArea = new JTextArea();
        frame.add(createScrollPane(textArea));
        textArea.setText(">");
        return frame;
    }

    protected static DockableFrame createSampleOutputFrame() {
        DockableFrame frame = createDockableFrame("Output", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME8));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
        frame.getContext().setInitIndex(0);
        frame.setLayout(new BorderLayout(2, 2));
        frame.add(createScrollPane(new JTextArea()));
        frame.add(new JTextField(), BorderLayout.AFTER_LAST_LINE);
        return frame;
    }

    protected static DockableFrame createSampleFindResult1Frame() {
        DockableFrame frame = createDockableFrame("Find Results 1", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME9));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
        frame.getContext().setInitIndex(0);
        JTextArea textArea = new JTextArea();
        frame.add(createScrollPane(textArea));
        textArea.setText("Find all \"TestDock\", Match case, Whole word, Find Results 1, All Open Documents\n" +
                "C:\\Projects\\src\\com\\jidesoft\\test\\TestDock.java(1):// TestDock.java : implementation of the TestDock class\n" +
                "C:\\Projects\\src\\jidesoft\\test\\TestDock.java(8):#import com.jidesoft.test.TestDock;\n" +
                "C:\\Projects\\src\\com\\jidesoft\\Test.java(10):#import com.jidesoft.test.TestDock;\n" +
                "Total found: 3    Matching files: 5    Total files searched: 5");
        return frame;
    }

    protected static DockableFrame createSampleFindResult2Frame() {
        DockableFrame frame = createDockableFrame("Find Results 2", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME10));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
        frame.getContext().setInitIndex(1);
        JTextArea textArea = new JTextArea();
        frame.add(createScrollPane(textArea));
        textArea.setText("Find all \"TestDock\", Match case, Whole word, Find Results 2, All Open Documents\n" +
                "C:\\Projects\\src\\com\\jidesoft\\test\\TestDock.java(1):// TestDock.java : implementation of the TestDock class\n" +
                "C:\\Projects\\src\\jidesoft\\test\\TestDock.java(8):#import com.jidesoft.test.TestDock;\n" +
                "C:\\Projects\\src\\com\\jidesoft\\Test.java(10):#import com.jidesoft.test.TestDock;\n" +
                "Total found: 3    Matching files: 5    Total files searched: 5");
        return frame;
    }

    protected static DockableFrame createSampleFindResult3Frame() {
        DockableFrame frame = createDockableFrame("Find Results 3", JideIconsFactory.getImageIcon(JideIconsFactory.DockableFrame.FRAME11));
        frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
        frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
        frame.getContext().setInitIndex(1);
        frame.add(createScrollPane(new JTextArea()));
        return frame;
    }


    private static JScrollPane createScrollPane(Component component) {
        JScrollPane pane = new JideScrollPane(component);
        pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return pane;
    }

    protected static JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();

        JMenu fileMenu = createFileMenu();
        //JMenu viewMenu = VsnetCommandBarFactory.createViewMenu(_frame);
        //JMenu windowMenu = createWindowsMenu();
        //JMenu optionMenu = createOptionMenu();
        //JMenu lnfMenu = CommandBarFactory.createLookAndFeelMenu(_frame);
        //JMenu helpMenu = createHelpMenu();

        menu.add(fileMenu);
        //menu.add(viewMenu);
        //menu.add(windowMenu);
        //menu.add(optionMenu);
        //menu.add(lnfMenu);
        // menu.add(helpMenu);


        return menu;
    }

    private static JMenu createFileMenu() {
        JMenuItem item;

        JMenu menu = new JideMenu("File");
        menu.setMnemonic('F');

        item = new JMenuItem("Exit");
        item.addActionListener(new AbstractAction() {
            private static final long serialVersionUID = -5359744447961331767L;

            public void actionPerformed(ActionEvent e) {
                clearUp();
            }
        });
        menu.add(item);
        return menu;
    }

    private static void clearUp() {
        _frame.removeWindowListener(_windowListener);
        _windowListener = null;

        if (_frame.getDockingManager() != null) {
            _frame.getDockingManager().saveLayoutData();
        }

        _frame.dispose();
        _frame = null;
    }
}