package com.bonsai.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.bonsai.controller.BonsaiController;
import com.bonsai.datatypes.*;
import com.bonsai.model.Tree;

import java.awt.Font;

public class EditDialog extends JDialog {

	private ArrayList<SpeciesInfo> _speciesList;
	private BonsaiController _controller;
	
	private boolean _newTree;
	
	
	private String[] _monthList = { 	"January",
										"February",
										"March",
										"April",
										"May",
										"June",
										"July",
										"August",
										"September",
										"Octobre",
										"November",
										"December" };
	
	// ************************************************************************************
	
	private final JPanel contentPanel = new JPanel();
	private JTextField _nameTextField;
	private JTextField _speciesTextField;
	private JTextField _typeTextField;
	private JTextField _familyTextField;
	private JTextField _groupTextField;
	private JTextField _cultivarTextField;
	private JTextField _genusTextField;
	private JTextField _otherNamesTextField;
	private JTextField _sourceTextField;
	private JTextField _containerTextField;
	private JTextField _priceTextField;
	private JTextField _heightTextField;
	private JTextField _widthTextField;

	private JComboBox _speciesComboBox;
	
	private JComboBox<String> _monthComboBoxAck;
	private JComboBox<String> _dayComboBoxAck;
	private JComboBox<String> _yearComboBoxAck;
	private JComboBox<String> _monthComboBoxBirth;
	private JComboBox<String> _dayComboBoxBirth;
	private JComboBox<String> _yearComboBoxBirth;
	private JComboBox<String> _monthComboBoxPot;
	private JComboBox<String> _dayComboBoxPot;
	private JComboBox<String> _yearComboBoxPot;
	
	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public EditDialog(ArrayList<SpeciesInfo> list, BonsaiController b) {
		
		this._speciesList = list;
		this._controller = b;
		this._newTree = true;;
		
		
		loadBasicGUI();
		
		loadSpeciesInfoGUI();
		
		loadDatesGUI();
		
	}
	
	public EditDialog(ArrayList<SpeciesInfo> list, BonsaiController b, Tree tree) {
		
		this._speciesList = list;
		this._controller = b;
		_newTree = false;
		
		loadBasicGUI();
		
		loadSpeciesInfoGUI();
		
		loadDatesGUI();
		
		loadTreeInfo(tree);
		
	}
	
	private void loadBasicGUI()
	{
		setBounds(100, 100, 350, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Bonsai Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(10, 14, 78, 14);
		contentPanel.add(lblName);
		
		_nameTextField = new JTextField();
		_nameTextField.setBounds(98, 11, 226, 20);
		contentPanel.add(_nameTextField);
		_nameTextField.setColumns(10);
		
		JLabel lblSource = new JLabel("Source");
		lblSource.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSource.setBounds(10, 300, 83, 14);
		contentPanel.add(lblSource);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPrice.setBounds(10, 350, 83, 14);
		contentPanel.add(lblPrice);
		
		JLabel lblContainer = new JLabel("Container");
		lblContainer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblContainer.setBounds(10, 325, 83, 14);
		contentPanel.add(lblContainer);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHeight.setBounds(10, 375, 46, 14);
		contentPanel.add(lblHeight);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWidth.setBounds(174, 375, 46, 14);
		contentPanel.add(lblWidth);
		
		_sourceTextField = new JTextField();
		_sourceTextField.setColumns(10);
		_sourceTextField.setBounds(77, 297, 247, 20);
		contentPanel.add(_sourceTextField);
		
		_containerTextField = new JTextField();
		_containerTextField.setColumns(10);
		_containerTextField.setBounds(77, 322, 247, 20);
		contentPanel.add(_containerTextField);
		
		_priceTextField = new JTextField();
		_priceTextField.setColumns(10);
		_priceTextField.setBounds(77, 347, 247, 20);
		contentPanel.add(_priceTextField);
		
		_heightTextField = new JTextField();
		_heightTextField.setColumns(10);
		_heightTextField.setBounds(56, 372, 108, 20);
		contentPanel.add(_heightTextField);
		
		_widthTextField = new JTextField();
		_widthTextField.setColumns(10);
		_widthTextField.setBounds(216, 373, 108, 20);
		contentPanel.add(_widthTextField);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				saveTree();
			}
		});
		submitButton.setBounds(235, 428, 89, 23);
		contentPanel.add(submitButton);
	}
	
	private void loadSpeciesInfoGUI()
	{
		
		_speciesComboBox = new JComboBox(_speciesList.toArray());
		_speciesComboBox.setBounds(98, 51, 155, 20);
		_speciesComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				int index = _speciesComboBox.getSelectedIndex();
				setLabels(_speciesList.get(index));
			}
		});
		
		
		contentPanel.add(_speciesComboBox);
		
		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSpecies.setBounds(10, 82, 46, 14);
		contentPanel.add(lblSpecies);
		
		JLabel lblGroup = new JLabel("Group");
		lblGroup.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGroup.setBounds(176, 82, 46, 14);
		contentPanel.add(lblGroup);
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setBounds(10, 107, 36, 14);
		contentPanel.add(lblType);
		
		JLabel lblCultivar = new JLabel("Cultivar");
		lblCultivar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCultivar.setBounds(176, 107, 46, 14);
		contentPanel.add(lblCultivar);
		
		JLabel lblFamily = new JLabel("Family");
		lblFamily.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFamily.setBounds(10, 132, 36, 14);
		contentPanel.add(lblFamily);
		
		JLabel lblGenus = new JLabel("Genus");
		lblGenus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGenus.setBounds(176, 132, 46, 14);
		contentPanel.add(lblGenus);
		
		JLabel lblOtherNames = new JLabel("Other Names");
		lblOtherNames.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOtherNames.setBounds(10, 157, 83, 14);
		contentPanel.add(lblOtherNames);
		
		_speciesTextField = new JTextField();
		_speciesTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_speciesTextField.setEditable(false);
		_speciesTextField.setBounds(56, 79, 108, 20);
		contentPanel.add(_speciesTextField);
		_speciesTextField.setColumns(10);
		
		_typeTextField = new JTextField();
		_typeTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_typeTextField.setEditable(false);
		_typeTextField.setColumns(10);
		_typeTextField.setBounds(56, 104, 108, 20);
		contentPanel.add(_typeTextField);
		
		_familyTextField = new JTextField();
		_familyTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_familyTextField.setEditable(false);
		_familyTextField.setColumns(10);
		_familyTextField.setBounds(55, 129, 109, 20);
		contentPanel.add(_familyTextField);
		
		_groupTextField = new JTextField();
		_groupTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_groupTextField.setEditable(false);
		_groupTextField.setColumns(10);
		_groupTextField.setBounds(216, 76, 108, 20);
		contentPanel.add(_groupTextField);
		
		_cultivarTextField = new JTextField();
		_cultivarTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_cultivarTextField.setEditable(false);
		_cultivarTextField.setColumns(10);
		_cultivarTextField.setBounds(216, 101, 108, 20);
		contentPanel.add(_cultivarTextField);
		
		_genusTextField = new JTextField();
		_genusTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_genusTextField.setEditable(false);
		_genusTextField.setColumns(10);
		_genusTextField.setBounds(216, 126, 108, 20);
		contentPanel.add(_genusTextField);
		
		_otherNamesTextField = new JTextField();
		_otherNamesTextField.setFont(new Font("Dialog", Font.PLAIN, 12));
		_otherNamesTextField.setEditable(false);
		_otherNamesTextField.setBounds(98, 154, 155, 20);
		contentPanel.add(_otherNamesTextField);
		_otherNamesTextField.setColumns(10);
		
		setLabels(_speciesList.get(0));
	}
	
	private void loadDatesGUI()
	{
		JLabel lblBirthDate = new JLabel("Birth Date");
		lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBirthDate.setBounds(10, 199, 83, 14);
		contentPanel.add(lblBirthDate);
		
		JLabel lblAcquiredDate = new JLabel("Acquired Date");
		lblAcquiredDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAcquiredDate.setBounds(10, 224, 83, 14);
		contentPanel.add(lblAcquiredDate);
		
		JLabel lblLastPotted = new JLabel("Last Potted");
		lblLastPotted.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLastPotted.setBounds(10, 249, 83, 14);
		contentPanel.add(lblLastPotted);
		
		_monthComboBoxBirth = new JComboBox(_monthList);
		_monthComboBoxBirth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				updateDayList(_monthComboBoxBirth.getSelectedItem().toString(), _dayComboBoxBirth);
			}
		});
		_monthComboBoxBirth.setBounds(98, 197, 99, 20);
		contentPanel.add(_monthComboBoxBirth);
		
		_dayComboBoxBirth = new JComboBox(getDayList(getNumDays(_monthComboBoxBirth.getSelectedItem().toString())));
		_dayComboBoxBirth.setBounds(207, 197, 46, 20);
		contentPanel.add(_dayComboBoxBirth);
		
		_yearComboBoxBirth = new JComboBox(getYearList());
		_yearComboBoxBirth.setBounds(260, 197, 64, 20);
		contentPanel.add(_yearComboBoxBirth);
		
		_monthComboBoxAck = new JComboBox(_monthList);
		_monthComboBoxBirth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				updateDayList(_monthComboBoxAck.getSelectedItem().toString(), _dayComboBoxAck);
			}
		});
		_monthComboBoxAck.setBounds(98, 222, 99, 20);
		contentPanel.add(_monthComboBoxAck);
		
		_dayComboBoxAck = new JComboBox(getDayList(getNumDays(_monthComboBoxAck.getSelectedItem().toString())));
		_dayComboBoxAck.setBounds(207, 222, 46, 20);
		contentPanel.add(_dayComboBoxAck);
		
		_yearComboBoxAck = new JComboBox(getYearList());
		_yearComboBoxAck.setBounds(260, 222, 64, 20);
		contentPanel.add(_yearComboBoxAck);
		
		_monthComboBoxPot = new JComboBox(_monthList);
		_monthComboBoxPot.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				updateDayList(_monthComboBoxPot.getSelectedItem().toString(), _dayComboBoxPot);
			}
		});
		_monthComboBoxPot.setBounds(98, 247, 99, 20);
		contentPanel.add(_monthComboBoxPot);
		
		_dayComboBoxPot = new JComboBox(getDayList(getNumDays(_monthComboBoxPot.getSelectedItem().toString())));
		_dayComboBoxPot.setBounds(207, 247, 46, 20);
		contentPanel.add(_dayComboBoxPot);
		
		_yearComboBoxPot = new JComboBox(getYearList());
		_yearComboBoxPot.setBounds(260, 247, 64, 20);
		contentPanel.add(_yearComboBoxPot);
	}
	
	private void setLabels(SpeciesInfo info)
	{
		_groupTextField.setText(info.get_group());
		_typeTextField.setText(info.get_type());
		_cultivarTextField.setText(info.get_cultivar());
		_familyTextField.setText(info.get_family());
		_genusTextField.setText(info.get_genus());
		_otherNamesTextField.setText(info.get_otherNames());
		_speciesTextField.setText(info.get_species());
		
		
	}
	
	private void updateDayList(String selectedMonth, JComboBox<String> box)
	{
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>( getDayList(getNumDays(selectedMonth)) );
		box.setModel( model );


	}
	
	private int getNumDays(String stringMonth)
	{
		int month = 0;
		
		for(int i=0; i<_monthList.length; i++)
		{
			if(_monthList[i].equalsIgnoreCase(stringMonth))
			{
				month = i+1;
				break;
			}
		}
		
		if(month == 2)
			return 28;
		else if(month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		else
			return 31;
			
			
	}
	
	private String[] getDayList(int numDays)
	{
		String[] list = new String[numDays];
		for(int i=0; i<numDays; i++)
			list[i] = "" + (i+1);
		
		return list;
	}
	
	private String[] getYearList()
	{
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("America/Seattle"));
		int curYear = gc.get(Calendar.YEAR);
		String[] list = new String[50];
		
		for(int i=0; i<50; i++)
			list[i] = "" + (curYear-i);
		
		return list;
	}
	
	private void loadTreeInfo(Tree tree)
	{
		_nameTextField.setText(tree.get_name().toString());
		_sourceTextField.setText(tree.get_source().toString());
		_containerTextField.setText(tree.get_container().toString());
		_priceTextField.setText(tree.get_price().toString());
		_heightTextField.setText(tree.get_height().toString());
		_widthTextField.setText(tree.get_width().toString());
		
		for(int i=0; i<_speciesList.size(); i++)
			if(_speciesList.get(i).get_commonName().equalsIgnoreCase(tree.get_speciesInfo().get_commonName())) {
				_speciesComboBox.setSelectedIndex(i);
				break;
			}
		
		GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("America/Seattle"));
		int curYear = gc.get(Calendar.YEAR);
		
		int birthYearindex, ackYearIndex, potYearIndex;
		birthYearindex = curYear - tree.get_birthDate().get_year();
		ackYearIndex = curYear - tree.get_acquiredDate().get_year();
		potYearIndex = curYear - tree.get_potted().get_year();
		
		_monthComboBoxAck.setSelectedIndex(tree.get_acquiredDate().get_month()-1);
		_dayComboBoxAck.setSelectedIndex(tree.get_acquiredDate().get_day()-1);
		_yearComboBoxAck.setSelectedIndex(ackYearIndex);
		
		_monthComboBoxBirth.setSelectedIndex(tree.get_birthDate().get_month()-1);
		_dayComboBoxBirth.setSelectedIndex(tree.get_birthDate().get_day()-1);
		_yearComboBoxBirth.setSelectedIndex(birthYearindex);
		
		_monthComboBoxPot.setSelectedIndex(tree.get_potted().get_month()-1);
		_dayComboBoxPot.setSelectedIndex(tree.get_potted().get_day()-1);
		_yearComboBoxPot.setSelectedIndex(potYearIndex);
	}
	
	private void saveTree()
	{
		if(_nameTextField.getText().trim().length() > 0)
		{
			Tree newTree = new Tree(new ItemNumber(0), new Name(_nameTextField.getText()));
			newTree.set_speciesInfo(_speciesList.get(_speciesComboBox.getSelectedIndex()));
			newTree.set_container(new Container(_containerTextField.getText()));
			newTree.set_height(new Height(_heightTextField.getText()));
			newTree.set_width(new Width(_widthTextField.getText()));
			newTree.set_price(new Price(_priceTextField.getText()));
			newTree.set_source(new Source(_sourceTextField.getText()));
			newTree.set_acquiredDate(new Acquired(_dayComboBoxAck.getSelectedIndex()+1, _monthComboBoxAck.getSelectedIndex()+1, Integer.parseInt(_yearComboBoxAck.getSelectedItem().toString())));
			newTree.set_birthDate(new BirthDate(_dayComboBoxBirth.getSelectedIndex()+1, _monthComboBoxBirth.getSelectedIndex()+1, Integer.parseInt(_yearComboBoxBirth.getSelectedItem().toString())));
			newTree.set_potted(new Potted(_dayComboBoxPot.getSelectedIndex()+1, _monthComboBoxPot.getSelectedIndex()+1, Integer.parseInt(_yearComboBoxPot.getSelectedItem().toString())));
			
			if(_newTree)
				_controller.get_treeController().addTree(newTree);
			else
			{
				_controller.get_treeController().editTree(newTree);
			}
			
			this.dispose();
		}
		
	}
}
