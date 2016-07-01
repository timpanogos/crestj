package org.opendof.tools.repository.interfaces.servlet.menu.allocate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.opendof.tools.repository.interfaces.da.SubRepositoryNode;

public class AllocateIdentifierOption implements Serializable{
	private static final long serialVersionUID = -4869063039188202303L;
	
	private String optionLabel;
	private Map<String, SubRepositoryNode> optionValues;
	private String selectedValue;
	private Map<String, AllocateIdentifierOption> childrenOptions;
	private boolean isRepoOption;
	
	public AllocateIdentifierOption(){	}

	public AllocateIdentifierOption(String optionLabel, Map<String, SubRepositoryNode> optionValues, String selectedValue, boolean isRepoOption){
		this.optionLabel = optionLabel;
		this.optionValues = optionValues;
		this.selectedValue = selectedValue;
		this.isRepoOption = isRepoOption;
		populateChildren();
	}
	
	public String getOptionLabel() {
		return optionLabel;
	}
	
	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}
	
	public List<String> getOptionValues() {
		return new ArrayList<String>(optionValues.keySet());
	}
	
	public void setOptionValues(Map<String, SubRepositoryNode> optionValues) {
		this.optionValues = optionValues;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	
	/**
	 * Get the path string to the leaf subrepository node of the selected options.
	 *  
	 * @return The path string to the leaf subrository ndoe of the selected options.
	 */
	public String getPath(){
		String delimiter = "/";
		AllocateIdentifierOption selectedChild = childrenOptions.get(selectedValue);
		if(isRepoOption){
			return selectedChild != null ? selectedChild.getPath() : "";
		}
		return selectedValue + (selectedChild != null ? delimiter + selectedChild.getPath() : "");
	}
	
	/**
	 * For the given selected option, return a flattened list which includes this option and all available child options.
	 * @return
	 */
	public List<AllocateIdentifierOption> getSelectedOptions(){
		List<AllocateIdentifierOption> options = new ArrayList<AllocateIdentifierOption>();
		options.add(this);

		AllocateIdentifierOption selectedOption = childrenOptions.get(selectedValue);
		if(selectedOption != null){
			options.addAll(selectedOption.getSelectedOptions());
		}
		return options;
	}
	
	/**
	 * Traverse SubRepositoryNode tree and populate children list.
	 */
	private void populateChildren(){
		childrenOptions = new HashMap<String, AllocateIdentifierOption>();
		for(Entry<String, SubRepositoryNode> entry: optionValues.entrySet()){
			String optionValue = entry.getKey();
			SubRepositoryNode node = entry.getValue();
			
			List<SubRepositoryNode> nodeChildren = node.getChildren();
			if(nodeChildren.isEmpty())
				continue;
						
			Map<String, SubRepositoryNode> valueToNodeMap = new HashMap<String, SubRepositoryNode>();
			
			String label = null;
			
			for(SubRepositoryNode nodeChild: nodeChildren){
				if(label == null){
					label = nodeChild.getLabel();//All children at the same depth should have the same label.
				}			
				
				//TODO: Temporary restriction on opendof repositories. Handle with user/group permissions in the future.
				if(nodeChild.getRepoType().equals("opendof") && nodeChild.getLabel().equals("Registry") && !nodeChild.getName().equals("1")){
					continue;
				}else if(nodeChild.getRepoType().equals("opendof") && nodeChild.getLabel().equals("Number of Bytes") && !nodeChild.getName().equals("4")){
					continue;
				}
		
				valueToNodeMap.put(nodeChild.getName(), nodeChild);
			}
			
			if(valueToNodeMap.isEmpty()){
				continue;
			}
			String defaultValue = new ArrayList<String>(valueToNodeMap.keySet()).get(0);
			AllocateIdentifierOption childOption = new AllocateIdentifierOption(label, valueToNodeMap, defaultValue, false);
			childrenOptions.put(optionValue, childOption);
		}
	}
}
