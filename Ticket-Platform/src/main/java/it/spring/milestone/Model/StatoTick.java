package it.spring.milestone.Model;

public enum StatoTick {  
	
	APERTO("Aperto"),
	CHIUSO("Chiuso"),  
	IN_CORSO("In Corso");
	    /*
	     * public static final StatoTick APERTO = new StatoTick("Aperto");
	    public static final StatoTick CHIUSO = new StatoTick("Chiuso");
	    public static final StatoTick IN_CORSO = new StatoTick("In Corso");
*/
	    private String valoreStato; //memorizza il valore: APERTO, CHIUSO, IN_CORSO

	     
	    private StatoTick(String valoreStato) {  
	        this.valoreStato = valoreStato;
	    }

	    public String getValoreVisualizzazione() { 
	        return valoreStato;
	    }
	}