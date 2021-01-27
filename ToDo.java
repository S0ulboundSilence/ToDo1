import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

public class ToDo {
	//Initialisierung von Variablen, Arrays usw.
	public static String[] todos = new String[10]; //Array auf dem die Aufgaben gespeichert werden
	public static LocalDate[] daten = new LocalDate[10]; //Array auf dem die Daten gespeichert werden
	public static String[] todos2 = new String[10]; //Array auf dem das todos-Array kopiert wird
	public static String[] daten2 = new String[10]; //Array auf dem das daten-Array kopiert wird
	
	static int i; //Variable die in for loops als index verwendet wird
	static String dateiName = "liste.txt"; //Name der Text-Datei auf der die Arrays gespeichert werden
	static boolean status = false; //Boolean mit der die erfolgreiche Ausführung einer Methode gespeichert wird
	static boolean aufsteigend = true; //Boolean um die Richtung der Sortierung festzulegen
	
	//Methode um alle Aufgaben aufzulisten
	public static String listeAlleAufgaben() {
		String liste = "Aufgaben auf der Liste:";
		for (i = 0; i < todos.length; i++) {
			if (todos[i] != null) {
				liste = liste+"\n"+todos[i];
			}
		}
		return liste;
	}
	
	//Methode um eine Aufgabe hinzuzufügen, funktioniert nur, wenn ein Platz frei ist und die Aufgabe noch nicht existiert
	public static boolean neueAufgabe(String titel) {
		for (i = 0; i < todos.length; i++) {
			if (todos[i] != null) {
				// Abbruch, falls Aufgabe bereits existiert
				if (todos[i].equals(titel)) {
					status = false;
					break;
				}
				else if (todos[todos.length-1] != null) { //Gebe return = false aus, wenn Array voll ist
					status = false;
					break;
				}
			}
			else { 
				// erstelle neue Aufgabe mit Default-Datum
				todos[i] = titel;
				status = setzeDatum(titel, 2020, 12, 23); //Gebe true zurück, wenn setzeDatum ausgeführt werden konnte
				break;
			}
		}
		return status;
	}
	
	//Methode um eine Aufgabe zu löschen
	public static boolean loescheAufgabe(String titel) {
		if (titel != null) {
			for (i = 0; i < todos.length; i++) {
				if (titel.equals(todos[i])) { //Falls Aufgabe existiert, wird die Aufgabe und ihr Datum gelöscht
					todos[i] = null; // lösche Aufgabe
					daten[i] = null; // lösche Datum
					status = true;
					for (int j = i+1; j<todos.length; j++) { //Verschiebe alle der gelöschten Aufgabe folgenden Aufgaben/Daten um eins nach vorne, dass zwischendrin kein null-Wert entsteht
						todos[j-1] = todos[j];
						daten[j-1] = daten[j];
					}
					todos[todos.length-1] = null; //Setze letzten Wert von todos auf null, damit ein freier Platz entsteht
					daten[daten.length-1] = null; //Setze letzten Wert von daten auf null, damit ein freier Platz entsteht
					break;
				}
				else { //Falls die Aufgabe nicht existiert, return = false
					status = false;
				}
			}
		}
		return status;
	}
	
	//Methode um den Namen einer Aufgabe zu bearbeiten, funktioniert nur wenn die Aufgabe existiert
	public static boolean bearbeiteAufgabe(String alterTitel, String neuerTitel) {
		for (i = 0; i < todos.length; i++) {
			if (alterTitel.equals(todos[i])) { //Wenn Aufgabe existiert, ändere Aufgabennamen
				todos[i] = neuerTitel;
				status = true;
				break;
			}
			else { //Wenn Aufgabe nicht existiert, return = false
				status = false;
			}
		}
		return status;
	}
	
	//Methode um das Datum einer Aufgabe zu setzen/bearbeiten. Erfolgreiche Datumsänderung nur, wenn eine gültige Aufgabe und ein gültiges Datum eingegeben wird
	public static boolean setzeDatum(String titel, int jahr, int monat,int tag) {
		for (i = 0; i < todos.length; i++) {
			if (titel.equals(todos[i])) { //Wenn Aufgabe existiert, setze Datum für die Aufgabe
				try {
				daten[i] = LocalDate.of(jahr, monat, tag);
				status = true;
				}
				catch (DateTimeException e) {
					System.out.println("Ungültige Eingabe.");
					status = false;
				}
				break;
			}
			else { //Wenn Aufgabe nicht existiert, return false
				status = false;
			}
		}
		return status;
	}
	
	//Methode um alle Aufgaben inkulsive Datum auszugeben
	public static String listeAlleAufgabenMitDatum() {
		String listeMitDaten = "Aufgaben auf der Liste:";
		for (i = 0; i < todos.length; i++) {
			if (todos[i] == null) { //Füge leeres Element auf die Liste, falls keine Aufgabe vorhanden
				listeMitDaten = listeMitDaten+ "";
			}
			else { //Füge Aufgabe zur Liste hinzu
				listeMitDaten = listeMitDaten + "\n"+ todos[i]+", fällig am "+ daten[i];
			}
		}
		return listeMitDaten;
	}
	
	//Methode, die die kopierten Arrays nach dem Titel sortiert
	public static String nachTitelSortieren(boolean aufsteigend) {
		copyArrays(todos, daten, todos2, daten2);
		if (aufsteigend == true) { //Aufsteigende Sortierung
			for (i = 0 ; i < todos2.length ; i++) {
				for (int j = i+1 ; j < todos2.length; j++) {
					if (todos2[i].compareTo(todos2[j]) > 0) {
						String temp = todos2[i];
						todos2[i] = todos2[j];
						todos2[j] = temp;
						String temp2 = daten2[i];
						daten2[i] = daten2[j];
						daten2[j] = temp2;
					}
				}
			}
		}
		if (aufsteigend == false) { //Absteigende Sortierung
			for (i = 0 ; i < todos2.length ; i++) {
				for (int j = i+1 ; j < todos2.length; j++) {
					if (todos2[i].compareTo(todos2[j]) < 0) {
						String temp = todos2[i];
						todos2[i] = todos2[j];
						todos2[j] = temp;
						String temp2 = daten2[i];
						daten2[i] = daten2[j];
						daten2[j] = temp2;
					}
				}
			}
		}
		return erstelleListe(todos2, daten2);
	}
	
	//Methode, die die kopierten Arrays nach dem Datum sortiert
	public static String nachDatumSortieren(boolean aufsteigend) {
		copyArrays(todos, daten, todos2, daten2);
		if (aufsteigend == true) {	//Aufsteigende Sortierung
			for (i = 0 ; i < daten2.length ; i++) {
				for (int j = i+1 ; j < daten2.length; j++) {
					if (daten2[i].compareTo(daten2[j]) > 0) {
						String temp = todos2[i];
						todos2[i] = todos2[j];
						todos2[j] = temp;
						String temp2 = daten2[i];
						daten2[i] = daten2[j];
						daten2[j] = temp2;
					}
				}
			}
		}
		if (aufsteigend == false) { //Absteigende Sortierung
			for (i = 0 ; i < daten2.length ; i++) {
				for (int j = i+1 ; j < daten2.length; j++) {
					if (daten2[i].compareTo(daten2[j]) < 0) {
						String temp = todos2[i];
						todos2[i] = todos2[j];
						todos2[j] = temp;
						String temp2 = daten2[i];
						daten2[i] = daten2[j];
						daten2[j] = temp2;
					}
				}
			}
		}
		return erstelleListe(todos2, daten2);
	}
	
	//Methode um die Arrays todos und daten in die Datei zu schreiben
	public static void writer(String filename, String[]arrayname, LocalDate[]arrayname2) throws IOException{
		BufferedWriter outWriter = null;
		outWriter = new BufferedWriter(new FileWriter(filename));
		for (int i = 0; i < arrayname.length; i++) {
			outWriter.write(arrayname[i] + ";" + arrayname2[i]);
			outWriter.newLine();
		}
		outWriter.flush();  
		outWriter.close();  
    }
	
	//Methode um die Daten aus der Datei auszulesen
	public static void reader(String filename, String[]arrayname, LocalDate[]arrayname2) throws IOException {
		File file = null;
	    Scanner reader = null;
		try {
			file = new File(filename);
		    reader = new Scanner(file);
		    for (i = 0; i < arrayname.length; i++) {
		    	if (reader.hasNextLine() == true) { //Überprüft, ob der scanner eine neue Zeile erkennt
			    	String data = reader.nextLine();
			    	String[] reading = data.split(";");
			        String datum = reading[1];
			        if (reading[1].equals("null")) { //Wenn Zeile leer, setze Werte in Arrays null
			        	arrayname[i] = null;
			        	arrayname2[i] = null;
			        }
			        else {							//Wenn die Zeile Inhalt hat, werden die Werte in die Arrays übernommen
			        	arrayname[i] = reading[0];
			        	arrayname2[i] = LocalDate.parse(datum);
			        }
		    	}
		    }
		 } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		 } finally {
		    	reader.close();
		 }
	}
	//Methode um die Arrays zu kopieren, zur vereinfachung der Sortierung werden null-Werte durch leere Werte ersetzt
	public static void copyArrays(String[]arrayname, LocalDate[]arrayname2, String[]arrayname3, String[]arrayname4) {
		for (i= 0; i < arrayname.length; i++) {
			if (arrayname[i] == null) {
				arrayname3[i] = "";
			}
			else {
				arrayname3[i] = arrayname[i];
			}
		}
		for (i= 0; i < arrayname2.length; i++) {
			if (arrayname2[i] == null) {
				arrayname4[i] = "";
			}
			else {
				arrayname4[i] = arrayname2[i].toString();
			}
		}
	}
	//Methode um zu überprüfen ob eine Aufgabe in einem Array existiert
	public static boolean checkeAufgabe(String titel, String[]arrayname) {
		copyArrays(todos, daten, todos2, daten2);
		for (i = 0; i<arrayname.length ; i++) {
			if (arrayname[i].equals(titel)) {
				status = true;
				break;
			}
			else {
				status = false;
			}
		}
		return status;
	}
	
	//Ausgabe-String wird erstellt und dann mit return zurückgegeben.
	public static String erstelleListe(String[]arrayname1, String[]arrayname2) {
		String sortierteListe = "Aufgaben auf der Liste:";
		for (i = 0; i < daten2.length; i++) {
			if (todos2[i] == null || todos2[i] == "") {
				sortierteListe = sortierteListe+ "";
			}
			else {
				sortierteListe = sortierteListe + "\n" + todos2[i]+", fällig am "+ daten2[i];
			}
		}
		return sortierteListe;
	}
	
	//Main
	public static void main (String[] args)
    {
		//Datei auslesen
		try {
			reader(dateiName,todos, daten);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
        //Dauerschleife die solange läuft, bis das Programm beendet wird
        while (true) {
        	System.out.println("Willkommen in Ihrer To-Do-Liste, was möchten Sie tun?\n[1] To-Dos anzeigen\n[2] Eintrag hinzufügen\n[3] Eintrag löschen\n[4] Programm beenden\n[5] Eintrag bearbeiten\n[6] Datum setzen\n[7] To-Dos mit Datum anzeigen\n[8] To-Dos sortieren");
        	int auswahl = scanner.nextInt();
        	scanner.nextLine();																												
	        if (auswahl == 1) {
	        	System.out.println(listeAlleAufgaben());
	        }																								
	        else if (auswahl == 2) {
	    		System.out.println("Bitte geben Sie einen Titel ein:");
	        	String neuerTitel = scanner.nextLine();
	        	if (neueAufgabe(neuerTitel) == true) {
	        		System.out.println("Aufgabe: " + neuerTitel + " wurde erfolgreich hinzugefügt.");
	        		try {
						writer(dateiName, todos, daten);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	        	else {
	        		System.out.println("Die Aufgabe " + neuerTitel + " konnte nicht hinzugefügt werden.");
	        	}
	        }																								
	        else if (auswahl == 3) {
	        	System.out.println("Bitte geben Sie einen Titel ein:");
	        	String titel = scanner.nextLine();
		        if (loescheAufgabe(titel) == true) {
		        	System.out.println("Die Aufgabe " + titel + " wurde gelöscht.");
		        }
		        else {
		        	System.out.println("Die Aufgabe " + titel + " wurde nicht gefunden.");
		        }
	        }
	        else if (auswahl == 4) {
	        	//Datei speichern
	        	try {
					writer(dateiName, todos, daten);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	scanner.close();
	        	System.exit(0);
	        }
	        else if (auswahl == 5) {
	        	System.out.println("Bitte geben Sie einen Titel ein:");
	        	String alterTitel = scanner.nextLine();
	        	if (checkeAufgabe(alterTitel, todos) == true) {
		        	System.out.println("Bitte geben Sie einen neuen Titel ein:");
		        	String neuerTitel = scanner.nextLine();
		        	if (bearbeiteAufgabe(alterTitel, neuerTitel) == true) {
		        		System.out.println("Die Aufgabe " + alterTitel + " wurde umbenannt zu " + neuerTitel + ".");
		        		try {
							writer(dateiName, todos, daten);
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		        	else {
		        		System.out.println("Die Aufgabe " + alterTitel + " konnte nicht umbenannt werden.");
		        	}
	        	}
	        	else {
	        		System.out.println("Die Aufgabe " + alterTitel + " wurde nicht gefunden.");
	        	}	
	        }																
	        else if (auswahl == 6) {
	        	System.out.println("Bitte geben Sie einen Titel ein:");
	        	String titel = scanner.nextLine();
	        	if(checkeAufgabe(titel, todos2) == true) {
		        	System.out.println("Bitte geben Sie ein Datum an");
		        	//Eingabe des Datums im Format YYYY MM DD
		        	int jahr = scanner.nextInt();
		        	int monat = scanner.nextInt();
		        	int tag = scanner.nextInt();
		        	if(setzeDatum(titel, jahr, monat, tag) == true) {
		        		System.out.println("Die Aufgabe "+ titel +" ist am " + jahr + "-" + monat + "-" + tag + " fällig.");
		        		try {
							writer(dateiName, todos, daten);
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		        	else {
		        		System.out.println("Das Datum für " + titel + " konnte nicht gesetzt werden.");
		        	}
	        	}
	        	else {
	        		System.out.println("Die Aufgabe " + titel + " wurde nicht gefunden.");
	        	}
	        }																												
	        else if (auswahl == 7 ) {
	        	System.out.println(listeAlleAufgabenMitDatum());
	        }																												
	        else if (auswahl == 8 ) {
	        	System.out.println("[1] nach Titel aufsteigend\n[2] nach Titel absteigend\n[3] nach Datum aufsteigend\n[4] nach Datum absteigend");
	        	auswahl = scanner.nextInt();
	        	scanner.nextLine();
	        	if (auswahl == 1) {
	        		System.out.println(nachTitelSortieren(true));
	        	}
	        	else if (auswahl == 2) {
	        		System.out.println(nachTitelSortieren(false));
	        	}
	        	else if (auswahl == 3) {
	        		System.out.println(nachDatumSortieren(true));
	        	}
	        	else if (auswahl == 4) {
	        		System.out.println(nachDatumSortieren(false));
	        	}
	        	else {
	        		System.out.println("Ungültige Eingabe.");
	        	}
	        }																												
	        else {
	        	System.out.println("Ungültige Eingabe.");
	        }
        }
    }
}
