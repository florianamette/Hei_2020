 1 - Récupérer les informations de quotation d'une valeur via la methode de classe getData de la classe API
 
 2 - Créer un objet market qui aura comme attribut une liste de code
 "GLE.PA", "ACA.PA", "ERA.PA" , "VK.PA" et 
 qui renverra les quotations pour chacun des codes
 Est-il judicieux de modifier le type d'objet de 'API' ?
 
 3 - Error Handling : 
 créer dans un trait Utils la méthode générique try catch pour 
 gérer les exceptions
 L'implémenter dans l'instanciation de la classe market
 -> What's happen ?
 
 4 - Case Class  -> p 82
 Créer une case classe OHLCV (Date, Open, High, Low, Close, Volume)
 Date : convertir unixtime to Date  
    import java.sql.Date
    new Date(???.toLong  * 1000)  
 Numérique type : Double.
 
 5 - Indicators
 Calculer les moyennes mobiles à 20 jours sur le cours de cloture
 => exercice : retourner les valeurs (actions) de la liste 
 qui ont un cours de clôture supérieur à leur mm20.
 Présenter les données OHLCV et l'écart en % avec les mm20.
 Hint : slice()
 

