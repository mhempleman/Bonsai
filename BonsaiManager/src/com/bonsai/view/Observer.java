package com.bonsai.view;

import java.util.ArrayList;

import com.bonsai.datatypes.BasicTreeData;
import com.bonsai.datatypes.SpeciesInfo;
import com.bonsai.model.Tree;

public interface Observer {
	
	public void receiveSpeciesUpdate(ArrayList<SpeciesInfo> speciesInfo);
	
	public void receiveTreeInformationUpdate(ArrayList<BasicTreeData> basicTreeData);
	
	public void receiveCurrentTreeUpdate(Tree tree, ArrayList<String> currentPictures);

}
