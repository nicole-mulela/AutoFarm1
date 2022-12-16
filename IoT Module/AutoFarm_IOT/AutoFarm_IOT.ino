#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <DHT.h>

#define FIREBASE_HOST "autofarm-b8fbb-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "AKTWK7IMheaEtnLd6NoV4RHySl8Ycf6CHJV1DH79"
#define WIFI_SSID "Siri"
#define WIFI_PASSWORD "Jaha@2020"

#define DHTPIN D2
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);

#define SensorPin A0          //pH meter Analog output to Arduino Analog Input 0
unsigned long int avgValue;  //Store the average value of the sensor feedback
float b, phValue;
int buf[10],temp;
#define offset -0.03

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  dht.begin();
  
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while(WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(500);
  }

  Serial.println();
  Serial.print("Connected");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP() );
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

  
}

void loop() {
  // put your main code here, to run repeatedly:
  
  float h =dht.readHumidity();
  float t =dht.readTemperature();

  if (isnan(h) || isnan(t))
  {
    Serial.println(F("Failed to read from DHT sensor! "));
    return;
  }

  //read from PH sensor
  for(int i=0;i<10;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(SensorPin);
  }
  for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(buf[i]>buf[j])
      {
        temp=buf[i];
        buf[i]=buf[j];
        buf[j]=temp;
      }
    }
  }  
  /// PH NEEDS CALIBRATION
  avgValue=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
    avgValue+=buf[i];
  phValue=(float)avgValue/1023*5; //convert the analog into millivolt
  phValue=(0.3*phValue+offset)-1.98;                      //convert the millivolt into pH value
  
  
  Serial.print("Humidity: ");
  Serial.print(h);
  Serial.print("%");
  String fireHumid = String(h);

  Serial.print(" Temperature: ");
  Serial.print(t);
Serial.print("°C");  
  String fireTemp =String(t);

  Serial.print(" pH:");  
  Serial.print(phValue,2);
  Serial.println(" ");
  String firePH =String(phValue);  

  delay(5000);


  Firebase.setString("/DHT11/Humidity", fireHumid);
  Firebase.setString("/DHT11/Temperature", fireTemp);
  Firebase.setString("/DHT11/PH", firePH);
    if (Firebase.failed())
    {
      Serial.print("pushing / logs failed: ");
      Serial.println(Firebase.error());
      return;
    }

}
