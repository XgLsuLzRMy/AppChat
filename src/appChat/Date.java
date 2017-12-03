package appChat;

public class Date {
	private int annee, mois, jour, heure, minute, seconde;
	
	public Date() {
		this.annee = 2017;
		this.mois = 1;
		this.jour = 1;
		this.heure = 0;
		this.minute = 0;
		this.seconde = 0;
	}
	
	// renvoie true si la date this est + ancienne que la date d
	public boolean superieur(Date d) {
		if ()
	}
	
	public int getAnnee() {
		return this.annee;
	}
	public int getMois() {
		return this.mois;
	}
	public int getJour() {
		return this.jour;
	}
	public int getHeure() {
		return this.heure;
	}
	public int getMinute() {
		return this.minute;
	}
	public int getSeconde() {
		return this.seconde;
	}
	public String toString() {
		String res = "" + this.annee + "/" + this.mois + "/" + this.jour + "/" + this.heure + "h" + this.minute + "min "+ this.seconde +"s";
		return res;
	}
}
