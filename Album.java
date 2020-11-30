public class Album {
	private String name;
	private String condition;
	private PhotoManager manager;

	public Album(String name, String condition, PhotoManager manager) {
		this.name = name;
		this.condition = condition;
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public String getCondition() {
		return condition;
	}

	public PhotoManager getManager() {
		return manager;
	}

	// Return all photos that satisfy the album condition
	public LinkedList<Photo> getPhotos() {
		BST<LinkedList<Photo>> invertedIndex = manager.getPhotos();
		String[] conditions = condition.split(" AND "); // array of wanted tags
		LinkedList<Photo> allPhotos = new LinkedList<>();
		copyContent(allPhotos, manager.getAllPhotos()); // retrieve and copies all photos
		// LinkedList<Photo> allPhotos = this.getAllPhotos();

		if (condition.equals("")) {
			return allPhotos;
		}

		for (int i = 0; i < conditions.length; i++) { // loop each condition
			if (invertedIndex.findKey(conditions[i])) { // if found check its photos
				LinkedList<Photo> list = invertedIndex.retrieve();
				allPhotos.findFirst();
				while (!allPhotos.currentIsNull()) { // loop through all photos and remove any nonexistent photo
					if (!findPhoto(allPhotos.retrieve(), list))
						allPhotos.remove();
					else
						allPhotos.findNext();
				}
			} else { // if not found then return an empty list, since condition is AND not or
				allPhotos = new LinkedList<>();
				return allPhotos;
			}
		}
		return allPhotos;
	}

	// Return the number of tag comparisons used to find all photos of the album
	public int getNbComps() {
		if (condition.equals(""))
			return 0;

		BST<LinkedList<Photo>> invertedIndex = manager.getPhotos();
		String[] conditions = condition.split(" AND ");
		int comparisons = 0;

		for (int i = 0; i < conditions.length; i++)
			comparisons += invertedIndex.countComparisons(conditions[i]);

		return comparisons;
	}

	// helper method to find is a photo exists within a list
	private boolean findPhoto(Photo p, LinkedList<Photo> list) {
		if (list.empty())
			return false; // photo not found

		list.findFirst();
		while (!list.last()) {
			if (list.retrieve().getPath().equals(p.getPath())) // if other photo exists return true
				return true;
			list.findNext();
		}
		if (list.retrieve().getPath().equals(p.getPath())) // check the last node
			return true;

		return false; // if traversed the entire list then no photo exist
	}

	// helper method to get all photos in manager and store it in a linked list
	@SuppressWarnings("unused")
	private LinkedList<Photo> getAllPhotos() {
		LinkedList<String> tags = manager.getPhotos().getAllTags(); // all tags of the tree
		LinkedList<Photo> allPhotos = new LinkedList<>(); // list to be returned

		tags.findFirst();
		while (!tags.currentIsNull()) {
			manager.getPhotos().findKey(tags.retrieve());
			LinkedList<Photo> list = manager.getPhotos().retrieve();// retrieve all its photos
			list.findFirst();
			while (!list.currentIsNull()) {
				if (!findPhoto(list.retrieve(), allPhotos))
					allPhotos.insert(list.retrieve());
				list.findNext();
			}
			tags.findNext();
		}
		return allPhotos;
	}

	@SuppressWarnings("unused")
	private void copyContent(LinkedList<Photo> list1, LinkedList<Photo> list2) {
		if (list2.empty())
			return;

		list2.findFirst();
		while (!list2.last()) {
			list1.insert(list2.retrieve());
			list2.findNext();
		}
		list1.insert(list2.retrieve()); // insert the last node
	}
}
