# Clothual

<br>

## INTRODUZIONE

Clothual è un’applicazione mobile Android per la gestione e organizzazione del proprio
guardaroba; un vero e proprio closet organizer a portata di cellulare!
L’idea nasce dal desiderio di proporre una soluzione alla celebre frase: «non ho nulla da
mettere!» ottimizzando le risorse e il tempo sprecato nella ricerca di outfit adatti ad ogni
occasione.
L’applicazione ci offre la possibilità di salvare, attraverso il semplice click della nostra
fotocamera, gli outfit migliori che abbiamo mai creato, suddividendo i capi che lo
compongono nelle diverse categorie proposte:

- scarpe
- pantaloni
- t-shirt
- felpe
- jeans

I capi possono essere visualizzati singolarmente, all’interno della categoria di appartenenza, o
nel complesso, proprio come nell’armadio di casa nostra! E’ inoltre possibile farne una
selezione che verrà inserita nei preferiti.
Il calendario, invece, ci permetterà di visualizzare gli outfit indossati nel giorno selezionato,
aiutando la nostra memoria a ricordare gli abbinamenti più apprezzati e le occasioni per cui
sono stati indossati.
Le statistiche nell’area personale, ci danno la possibilità di visualizzare, per ogni categoria, la
percentuale di capi di cui è composta e i tre colori maggiormente presenti nel nostro armadio
virtuale. Un piccolo aiuto per gli acquisti futuri! Qui subentra la mappa che permette di
visualizzare i negozi nelle vicinanze, qualora volessimo concederci del sano e meritato
shopping!


<br><br>

## FUNZIONALITA' OFFERTE
Al primo avvio dell'applicazione viene richiesto all’utente di eseguire il login con le
credenziali o di registrarsi attraverso i servizi di Google. Una volta avvenuta l’autenticazione,
l’utente si trova nella schermata home dove può scegliere di visualizzare le seguenti sezioni:

- la sezione comprendente tutti gli abiti
- le sezioni suddivise per categoria di capi d’abbigliamento
- la mappa

Attraverso il menu posto nella navigation bar situata sul fondo dell’applicazione, l’utente può
accedere alle seguenti schermate:

- la schermata home
- la sezione comprendente i capi preferiti
- la fotocamera: da cui scattare foto o caricare foto dalla galleria per poterle aggiungere al guardaroba.
- il calendario: in cui visualizzare gli outfit inseriti.
- l’area personale: da cui si può modificare il profilo, modificare le impostazioni dell’app, visualizzare le statistiche del guardaroba, visualizzare le informazioni dell’applicazione oppure eseguire il logout dal proprio account.

<br><br>

## ARCHITETTURA
<br>

### **API** Utilizzate:
Abbiamo utilizzato diverse API per implementare le seguenti funzioni:
1. Mappa 
    *   org.osmdroid:osmdroid-android:6.1.2
2. Firebase
    *   com.google.android.gms:play-services-auth:20.4.1
    *   com.google.firebase:firebase-auth:21.1.0
    *   com.google.firebase:firebase-firestore:24.4.2
3. Home
    *   androidx.gridlayout:gridlayout:1.0.0
4. Room
    *   androidx.room:room-runtime:$room_version
    *   androidx.room:room-compiler:$room_version

<br>

### **Services** Utilizzati:
Abbiamo utilizzato i seguenti service:
1. Open Street Map
    *    Open Street Maps: abbiamo utilizzato Open Street Maps per visualizzare la mappa.
2. Firebase
    *    Firebase Authentication: grazie a questo servizio possiamo gestire
l’autenticazione dell’utente (tramite Google oppure manualmente
inserendo i dati richiesti), eseguire il sing in automaticamente dopo il
primo accesso e il sign out.
    *    Cloud Firestore: questo servizio rende possibile il salvataggio dei dati dell’utente in un realtime database.

<br>

### **ViewModels** Utilizzati:
Delegano al Repository l’estrazione dei dati.
* AddDressModel
* CalendarModel
* CategoriesModel
* HomeModel
* LoginModel
* MapModel
* PersonalModel
* PhotoMOdel
* RegistrationModel

<br>

### **Models** Utilizzati:
* Clothual
* Image
* Outfit
* User

<br>

### **Repository**:
Interrogano il database per ottenere i dati necessari richiesti dal ViewModel.
Room si occupa di creare e gestire un DB locale. Permette allo sviluppatore di non
interfacciarsi direttamente con SQLite, ma di utilizzare degli oggetti che automaticamente
gestiranno il DB locale. Sono inoltre collegati anche Google per i servizi di autenticazione e
registrazione e Open Street Map per la mappa.


<br>

### **Util**:
* Constant: contiene tutte le costanti necessarie per il progetto
*  Query: contiene tutte le query utilizzate per interfacciarsi con il DB locale
*  Converters: converte l’attributo Clothual list della classe outfit in una stringa da registrare
nel database
* SharedPreferenceReadWrite: legge e scrive le shared preference
