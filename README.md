## ** A Kafka Consumer with Spring boot plus apache camel and send grid integration**
This project has been made to demonstrate how apache camel can be integrated with sendgrid 
In this scenario a event will trigger the microintegration, as a result the client will
receive a email according to the specification of its event.

## **USED TECHNOLOGIES**
Java SpringBoot with Apache Camel plus Send Grid and a kafka integration wich is the source
of the events that triggers the email sending.


## **SETUP**
file.path=this is the source folder appended by the file name
noop.config=this is the config will control if file will be moved or not
timeout.polling=tolerance time in case the file is missing 
sendgrid.api.key=the api key used to auth


## **Running the Service**
In this example you will have to configura kafka and a publisher.
You must create your own files in the folder specified by the configs as above describe.