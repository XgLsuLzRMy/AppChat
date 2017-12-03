package appChat;

public class Date {
	int annee, mois, jour, heure, minute, seconde;
	
	public Date() {
		this.annee = 2017;
		this.mois = 1;
		this.jour = 1;
		this.heure = 0;
		this.minute = 0;
		this.seconde = 0;
	}
	
	public String toString() {
		String res = "" + this.annee + "/" + this.mois + "/" + this.jour + "/" + this.heure + "h" + this.minute + "min "+ this.seconde +"s";
		return res;
	}
}
