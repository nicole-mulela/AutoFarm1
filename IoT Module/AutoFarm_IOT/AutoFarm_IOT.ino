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
  
  Serial.print("Humidity: ");
  Serial.print(h);
  String fireHumid = String(h) + String("%");

  Serial.print("% Temperature: ");
  Serial.print(t);
  Serial.println("°C");
  String fireTemp =String(t) + String("°C");
  delay(5000);


  Firebase.pushString("/DHT11/Humidity", fireHumid);
  Firebase.pushString("/DHT11/Temperature", fireTemp);
    if (Firebase.failed())
    {
      Serial.print("pushing / logs failed: ");
      Serial.println(Firebase.error());
      return;
    }

}
