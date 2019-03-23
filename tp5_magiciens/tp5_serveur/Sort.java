package tp5_serveur;

public enum Sort {
	FLECHE_GLACE,
	SPHERE_GLACE,
	FLECHE_FEU,
	SPHERE_FEU,
	FLECHE_EAU,
	GUERISON_EAU,
	INVISIBLE;
	
	public String toString() {
		switch(this) {
		case FLECHE_EAU:
			return "flèche d'eau";
		case FLECHE_FEU:
			return "flèche de feu";
		case FLECHE_GLACE:
			return "flèche de glace";
		case GUERISON_EAU:
			return "eau de guerison";
		case SPHERE_FEU:
			return "sphère de feu";
		case SPHERE_GLACE:
			return "sphère de glace";
		case INVISIBLE:
			return "invisibilité";
		}
		return "sort inconu";
	}
	
	public boolean hasEnemy() {
		if(this == Sort.FLECHE_EAU ||
		   this == Sort.FLECHE_FEU ||
		   this == Sort.FLECHE_GLACE )
			return true;
		return false;
	}
}
