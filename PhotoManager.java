
public class PhotoManager {
	private LinkedList<Photo> manager;
	private BST<LinkedList<Photo>> invertedIndex;

	public PhotoManager() {
		manager = new LinkedList<>();
		invertedIndex = new BST<>();
	}

	public void addPhoto(Photo p) {
		LinkedList<String> tagsOfP = p.getTags();

		if (tagsOfP == null) { // a photo MUST have a tag
			//System.out.println("Can't be added tag is null");
			return;
		}
		if(tagsOfP.empty()) { //only add it to the list
			manager.insert(p);
			//System.out.println("Empty tag");
			return;
		}
		
		// check if the path is unique
		if (uniquePath(p))
			manager.insert(p);
		else {
			//System.out.println("Can't be added path is not unique");
			return;
		}

		tagsOfP.findFirst();
		while (!tagsOfP.currentIsNull()) {
			if (invertedIndex.findKey(tagsOfP.retrieve())) { // if the tag exists update it
				invertedIndex.retrieve().insert(p);
			} else {
				LinkedList<Photo> pList = new LinkedList<>();
				pList.insert(p);
				invertedIndex.insert(tagsOfP.retrieve(), pList); // if tag is new insert it and its photo
			}
			tagsOfP.findNext();
		}
	}

	public void deletePhoto(String path) {
		LinkedList<String> tags = deleteFromList(path);
		if (tags.empty()) {
			//System.out.println("Unable to delete the photo");
			return;
		}
		tags.findFirst();
		while (!tags.currentIsNull()) {
			invertedIndex.findKey(tags.retrieve()); // find the key
			LinkedList<Photo> p = invertedIndex.retrieve(); // retrieve its photo
			p.findFirst();
			while (!p.currentIsNull()) {
				if (p.retrieve().getPath().equals(path))
					p.remove(); // remove the photo
				else
					p.findNext();
			}
			if (invertedIndex.retrieve().empty()) // if the tags list is empty remove it from the tree
				invertedIndex.removeKey(tags.retrieve());
			tags.findNext();
		}
	}

	public BST<LinkedList<Photo>> getPhotos() {
		return invertedIndex;
	}

	protected LinkedList<Photo> getAllPhotos() {
		return manager;
	}

	/*
	 *** HELPER METHODS ***
	 */

	// method that checks if a photo is unique
	private boolean uniquePath(Photo p) {
		if (manager.empty())
			return true; // empty list, no other path

		manager.findFirst();
		while (!manager.last()) {// until last node
			if (manager.retrieve().getPath().equals(p.getPath())) // if other photo exists return false
				return false;
			manager.findNext();
		}
		if (manager.retrieve().getPath().equals(p.getPath())) // if other photo exists return false
			return false;

		return true;
	}

	// method that deletes a photo from the list
	private LinkedList<String> deleteFromList(String path) {
		LinkedList<String> tags = new LinkedList<>();
		manager.findFirst();
		while (!manager.currentIsNull()) {
			if (manager.retrieve().getPath().equals(path)) {
				tags = manager.retrieve().getTags(); // retrieve all the photo's tags
				manager.remove();
				return tags;
			}
			manager.findNext();
		}
		return tags; // returns an empty list if path is not found
	}
	
	//add to PhotoManager
	  @Override
	    public String toString() {
		return invertedIndex.toString();
	    }

}
