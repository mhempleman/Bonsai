package com.bonsai.view;



import com.bonsai.text.*;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JTree;

import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.bonsai.controller.BonsaiController;
import com.bonsai.datatypes.BasicTreeData;
import com.bonsai.model.Tree;



















import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.swing.Box;

import org.jdesktop.swingx.border.DropShadowBorder;

public class MainWindow {

	private JFrame _frame;
	private JPanel _mainPanel;
	
	private BonsaiView _view;
	private BonsaiController _controller;
	private Tree _currentTree;
	
	private JTree _jtree;
	
	// test  *****************************
	
	private JLabel _testLabel;
	private JPanel _picturePane;
	private ImagePanel _picPanel;
	private PicPanel _picPanel2;
	
	private int _currentPicIndex;
	

	
	// test   ********************************
	
	

	/**
	 * Create the application.
	 */
	public MainWindow(BonsaiView view, BonsaiController bm) {
		this._view = view;
		this._controller = bm;
		this.set_currentTree(null);
		
		initialize();
		
		
	}
	
	public Tree get_currentTree() {
		return _currentTree;
	}

	private void set_currentTree(Tree _currentTree) {
		this._currentTree = _currentTree;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		_frame = new JFrame();
		_frame.setVisible(true);
		_frame.setBounds(100, 100, 900, 600);
		_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		_frame.addWindowListener(new CloseListener(this._frame)); 
		
		_frame.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		    	_view.getPic(true, _currentPicIndex-1);
		    }

			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentShown(ComponentEvent arg0) {}
		});
		
		try {
            
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } 
		    catch (UnsupportedLookAndFeelException e) {
		       // handle exception
		    }
		    catch (ClassNotFoundException e) {
		       // handle exception
		    }
		    catch (InstantiationException e) {
		       // handle exception
		    }
		    catch (IllegalAccessException e) {
		       // handle exception
		    }
		
		loadMenu();
		loadToolbar();
		loadTreePanel();
		loadMainPanel();
		
		
	}
	
	private void loadMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(_frame, "Are you sure you want to exit?");
				if(result == 0) //0=yes, 1=no, 2=cancel, -1=close	
					exitApplication();
				
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnTree = new JMenu("Tree");
		menuBar.add(mnTree);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_view.loadEditDialog(_view.get_speciesInformation(), _view, null);
			}
		});
		mnTree.add(mntmAdd);
		
		JMenuItem mntmEdit = new JMenuItem("Edit");
		mntmEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_view.loadEditDialog(_view.get_speciesInformation(), _view, _currentTree);
			}
		});
		mnTree.add(mntmEdit);
		
		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int result = JOptionPane.showConfirmDialog(_frame, "Are you sure you want to remove " + _currentTree +"?");
				if(result == 0) //0=yes, 1=no, 2=cancel, -1=close
					_controller.get_treeController().removeCurrentTree();
			}
		});
		mnTree.add(mntmRemove);
		
		JMenu mnPictures = new JMenu("Pictures");
		menuBar.add(mnPictures);
		
		JMenuItem mntmAdd_1 = new JMenuItem("Add");
		mntmAdd_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_controller.get_documentController().addPictures(_currentTree.get_name().toString(), _mainPanel);
			}
		});
		mnPictures.add(mntmAdd_1);
		
		JMenuItem mntmRemove_1 = new JMenuItem("Remove");
		mntmRemove_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_controller.get_documentController().removePictures(_currentTree.get_name().toString(), _mainPanel);
			}
		});
		mnPictures.add(mntmRemove_1);
		
		JMenu mnDocuments = new JMenu("Documents");
		menuBar.add(mnDocuments);
		
		JMenuItem mntmAdd_2 = new JMenuItem("Add");
		mntmAdd_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_controller.get_documentController().addDocuments(_currentTree.get_name().toString(), _mainPanel);
			}
		});
		mnDocuments.add(mntmAdd_2);
		
		JMenuItem mntmRemove_2 = new JMenuItem("Remove");
		mntmRemove_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_controller.get_documentController().removeDocuments(_currentTree.get_name().toString(), _mainPanel);
			}
		});
		mnDocuments.add(mntmRemove_2);
		
		JMenuItem mntmBrowse = new JMenuItem("Browse");
		mntmBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_controller.get_documentController().editDocument(_currentTree.get_name().toString(), _mainPanel);
			}
		});
		mnDocuments.add(mntmBrowse);
		
		
	}
	
	private void loadToolbar()
	{
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		
		Image temp = null;

		
		try
		{
			temp = ImageIO.read(getClass().getClassLoader().getResource("tree-icon.png"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JButton addTreeButton = new JButton(new ImageIcon(temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		addTreeButton.setFocusPainted(false);
		addTreeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_view.loadEditDialog(_view.get_speciesInformation(), _view, null);
			}
		});
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 50));
		toolBar.add(rigidArea);
		addTreeButton.setToolTipText("Add Tree");
		addTreeButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolBar.add(addTreeButton);
		
		
		try
		{
			temp = ImageIO.read(getClass().getClassLoader().getResource("doc.png"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JButton browseDocButton = new JButton(new ImageIcon(temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		browseDocButton.setFocusPainted(false);
		browseDocButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_controller.get_documentController().editDocument(_currentTree.get_name().toString(), _mainPanel);
			}
		});
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 20));
		toolBar.add(rigidArea_1);
		
		
		browseDocButton.setToolTipText("Browse Documents");
		browseDocButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolBar.add(browseDocButton);
		
		
		try
		{
			temp = ImageIO.read(getClass().getClassLoader().getResource("cameras_icon.png"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JButton browsePicButton = new JButton(new ImageIcon(temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		browsePicButton.setFocusPainted(false);
		browsePicButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
		});
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(5, 20));
		toolBar.add(rigidArea_2);
		
		
		browsePicButton.setToolTipText("Browse Pictures");
		browsePicButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolBar.add(browsePicButton);
		
		
		try
		{
			temp = ImageIO.read(getClass().getClassLoader().getResource("file.png"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JButton fileButton = new JButton(new ImageIcon(temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		fileButton.setFocusPainted(false);
		fileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
		});
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(5, 20));
		toolBar.add(rigidArea_3);
		
		
		fileButton.setToolTipText("Species Database");
		fileButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		toolBar.add(fileButton);
		
		
		
		_frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		
		
	}
	
	private void loadMainPanel()
	{
		_mainPanel = new JPanel();
		_frame.getContentPane().add(_mainPanel, BorderLayout.CENTER);
		_mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel mainPanelSouth = new JPanel();
		mainPanelSouth.setPreferredSize(new Dimension(10, 25));
		_mainPanel.add(mainPanelSouth, BorderLayout.SOUTH);
		
		JPanel mainPanelNorth = new JPanel();
		mainPanelNorth.setPreferredSize(new Dimension(10, 5));
		_mainPanel.add(mainPanelNorth, BorderLayout.NORTH);
		
		JPanel mainPanelEast = new JPanel();
		_mainPanel.add(mainPanelEast, BorderLayout.EAST);
		
		JPanel mainPanelWest = new JPanel();
		_mainPanel.add(mainPanelWest, BorderLayout.WEST);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(null);
		_mainPanel.add(tabbedPane);
		
		loadMainPane(tabbedPane);
		loadPicturePane(tabbedPane);
		loadNotesPane(tabbedPane);
	}
	
	private void loadMainPane(JTabbedPane tabbedPane)
	{
		JPanel mainPane = new JPanel();
		mainPane.setBorder(null);
		mainPane.setBackground(Color.WHITE);
		tabbedPane.addTab("Main", null, mainPane, null);
		
		_testLabel = new JLabel("");
		_testLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mainPane.add(_testLabel);
	}
	
	private void loadPicturePane(JTabbedPane tabbedPane)
	{
		_picturePane = new JPanel();
		_picturePane.setBackground(Color.WHITE);
		_picturePane.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Pictures", null, _picturePane, null);
		
		loadPictureToolbar(_picturePane);
		
		
		
		
		
	}
	
	private void loadPictureToolbar(JPanel picturePane)
	{
		
		JToolBar pictureToolBar = new JToolBar();
		pictureToolBar.setBackground(picturePane.getBackground());
		pictureToolBar.setFloatable(false);
		picturePane.add(pictureToolBar, BorderLayout.NORTH);
		Image temp = null;

		
		try
		{
			temp = ImageIO.read(getClass().getClassLoader().getResource("leftArrow.png"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JButton leftButton = new JButton(new ImageIcon(temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_view.getPic(false, _currentPicIndex);
			}
		});
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(40, 20));
		pictureToolBar.add(rigidArea);
		leftButton.setBackground(Color.WHITE);
		leftButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		pictureToolBar.add(leftButton);
		
		try
		{
			temp = ImageIO.read(getClass().getClassLoader().getResource("rightArrow.png"));
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		JButton rightButton = new JButton(new ImageIcon(temp.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				_view.getPic(true, _currentPicIndex);
			}
		});
		
		Component horizontalGlue = Box.createHorizontalGlue();
		pictureToolBar.add(horizontalGlue);
		rightButton.setBackground(Color.WHITE);
		rightButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		pictureToolBar.add(rightButton);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_1.setPreferredSize(new Dimension(40, 20));
		pictureToolBar.add(rigidArea_1);
	}
	
	private void loadNotesPane(JTabbedPane tabbedPane)
	{
		JPanel notesPane = new JPanel();
		notesPane.setBackground(Color.WHITE);
		tabbedPane.addTab("Notes", null, notesPane, null);
		
		final JTextPane textPane = new JTextPane();
	    JScrollPane scrollPane = new JScrollPane(textPane);

	    JPanel north = new JPanel();
	    
	    north.setBackground(Color.WHITE);

	    JMenuBar menu = new JMenuBar();
	    JMenu styleMenu = new JMenu();
	    styleMenu.setText("Style");
	    
	    JButton save = new JButton();
	    save.setText("Save");
	    save.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent arg0) {
	 
	    		saveText((StyledDocument)textPane.getDocument());
	    	}
	    });

	    Action boldAction = new BoldAction();
	    boldAction.putValue(Action.NAME, "Bold");
	    styleMenu.add(boldAction);

	    Action italicAction = new ItalicAction();
	    italicAction.putValue(Action.NAME, "Italic");
	    styleMenu.add(italicAction);

	    Action foregroundAction = new ForegroundAction();
	    foregroundAction.putValue(Action.NAME, "Color");
	    styleMenu.add(foregroundAction);

	    Action formatTextAction = new FontAndSizeAction();
	    formatTextAction.putValue(Action.NAME, "Font and Size");
	    styleMenu.add(formatTextAction);

	    menu.add(styleMenu);
	    menu.add(save);

	    north.add(menu);
	    notesPane.setLayout(new BorderLayout());
	    notesPane.add(north, BorderLayout.NORTH);
	    notesPane.add(scrollPane, BorderLayout.CENTER);
	    
	    try
	    {
	    	File file = new File("c:/users/matt/desktop/out.html");
	        textPane.setPage(file.toURI().toURL());
	    	
	    }
	    catch (IOException e)
	    {
	    	
	    	e.printStackTrace();
	    }
	    
	    /*HTMLEditorKit kit = new HTMLEditorKit();
	    BufferedReader in;
	    StyledDocument doc = (StyledDocument)textPane.getDocument();
	    
	    try {
            in = new BufferedReader(new FileReader("c:/users/matt/desktop/out.txt"));

            kit.read(in, doc,  doc.getLength());

        } catch (FileNotFoundException e) {
        		e.printStackTrace();
        } catch (IOException e){
        	e.printStackTrace();
        } catch (BadLocationException e){
        	e.printStackTrace();
        }*/
	}
	
	private void saveText(StyledDocument doc)
	{
		HTMLEditorKit kit = new HTMLEditorKit();
		BufferedOutputStream out;

		
		try {
            out = new BufferedOutputStream(new FileOutputStream("c:/users/matt/desktop/out.html"));

            kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());

        } catch (FileNotFoundException e) {
        		e.printStackTrace();
        } catch (IOException e){
        	e.printStackTrace();
        } catch (BadLocationException e){
        	e.printStackTrace();
        }
	}
	
	private void loadTreePanel()
	{
		JPanel treePanel = new JPanel();
		_frame.getContentPane().add(treePanel, BorderLayout.WEST);
		treePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel treePanelSouth = new JPanel();
		treePanelSouth.setPreferredSize(new Dimension(10, 25));
		treePanel.add(treePanelSouth, BorderLayout.SOUTH);
		
		JPanel treePanelEast = new JPanel();
		treePanelEast.setPreferredSize(new Dimension(25, 10));
		treePanel.add(treePanelEast, BorderLayout.EAST);
		
		JPanel treePanelWest = new JPanel();
		treePanelWest.setPreferredSize(new Dimension(25, 10));
		treePanel.add(treePanelWest, BorderLayout.WEST);
		
		JPanel treePanelNorth = new JPanel();
		treePanelNorth.setPreferredSize(new Dimension(10, 25));
		treePanel.add(treePanelNorth, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.white);
		treePanel.add(scrollPane, BorderLayout.CENTER);
		
		_jtree = new JTree();
		_jtree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Forest") ));
		
			_jtree.setBorder(null);
			_jtree.setPreferredSize(new Dimension(200, 0));
			
			_jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			
			_jtree.addTreeSelectionListener(new TreeSelectionListener() {
		      public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
		     
		        
		        _controller.get_treeController().updateCurrentTree(node.getUserObject().toString());
		        
		      }

			
		    });
		
		scrollPane.setViewportView(_jtree);
	}
	
	public void updateJTree(ArrayList<BasicTreeData> treeData)
	{
		
		_jtree.setModel(new DefaultTreeModel( new DefaultMutableTreeNode("Forest") ));
		
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) _jtree.getModel().getRoot();
		DefaultTreeModel model = (DefaultTreeModel) _jtree.getModel();
		
		
		
		for(BasicTreeData member : treeData)
		{
			
			for(int i=0; i<root.getChildCount(); i++)
			{
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
				int index = node.getChildCount();
				
				boolean found = false;
				
				if(node.getUserObject().toString().equals(member.get_group()))
				{
					for(int j=0; j < node.getChildCount(); j++)
					{
						DefaultMutableTreeNode node2 = (DefaultMutableTreeNode) node.getChildAt(j);
						
						if(node2.getUserObject().toString().equals(member.get_name()))
						{
							found = true;
							break;
						}
					}
					
					if(!found)
					{
						model.insertNodeInto(new DefaultMutableTreeNode(member.get_name()), node, index);
					}
					else
						found = false;
					
					break;
				}
				
				if(i == root.getChildCount()-1)
				{
					model.insertNodeInto(new DefaultMutableTreeNode(member.get_group()), (DefaultMutableTreeNode)root, root.getChildCount());
					model.insertNodeInto(new DefaultMutableTreeNode(member.get_name()), (DefaultMutableTreeNode)root.getChildAt(root.getChildCount()-1), 0);
					
				}
				
				
			}
			
			if(root.getChildCount() == 0)
			{
				model.insertNodeInto(new DefaultMutableTreeNode(member.get_group()), (DefaultMutableTreeNode)root, root.getChildCount());
				model.insertNodeInto(new DefaultMutableTreeNode(member.get_name()), (DefaultMutableTreeNode)root.getChildAt(root.getChildCount()-1), 0);
			}
			
			
		}
		
		_jtree.expandRow(0);
	}
	
	protected void updateCurrentTreeData(Tree currentTree)
	{
		if(currentTree == null)
			this._testLabel.setText(" ");
		else {
			this._currentTree = currentTree;
			this._testLabel.setText(currentTree.get_name() + "  " + currentTree.get_acquiredDate().toString());
		}
			
	}
	
	protected void updatePictureData(ArrayList<String> currentPictures, int index)
	{
		this._currentPicIndex = index;
		
		if(_picPanel != null)
		{
			_picturePane.remove(_picPanel);
			_picPanel = null;
			_picturePane.repaint();
			
		}
		
		if(currentPictures != null && currentPictures.size() > index)
		{
			
			
			_picPanel = new ImagePanel(new Dimension(_picturePane.getWidth(),_picturePane.getHeight()-50), currentPictures.get(index));
			//_picPanel2 = new PicPanel();
			//_picPanel2.setImage(currentPictures.get(index), new Dimension(_picturePane.getWidth()-20,_picturePane.getHeight()-60));
			_picPanel.setBackground(_picturePane.getBackground());
			
			/*DropShadowBorder shadow = new DropShadowBorder();
	        shadow.setShadowColor(Color.BLACK);
	        shadow.setShowLeftShadow(true);
	        shadow.setShowRightShadow(true);
	        shadow.setShowBottomShadow(true);
	        shadow.setShowTopShadow(true);
			_picPanel2.setBorder(shadow);*/
			
	        
			_picturePane.add(_picPanel, BorderLayout.CENTER);
			
		
			_picturePane.validate();
		}
	}
	
	private void exitApplication()
	{
		_controller.save();
		System.exit(0);
	}
	
	// close listener **********************************************************************
	
	class CloseListener implements WindowListener
	{
		public CloseListener(Component component)
		{
			this.component = component;
		}
		
		public void windowDeactivated(WindowEvent event){}
		public void windowActivated(WindowEvent event){}
		public void windowDeiconified(WindowEvent event){}
		public void windowIconified(WindowEvent event){}
		public void windowClosed(WindowEvent event){}
		public void windowClosing(WindowEvent event)
		{
			int result = JOptionPane.showConfirmDialog(component, "Are you sure you want to exit?");
			if(result == 0)//0=yes, 1=no, 2=cancel, -1=close
				exitApplication();
		}
		public void windowOpened(WindowEvent event){}
	
		private Component component;
	} 
}
