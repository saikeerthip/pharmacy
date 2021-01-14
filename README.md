# pharmacy

Run all the three individual services and then execute the below curl.

## Sample Curl to get the prescription

curl --request POST \
  --url http://localhost:8441/api/v1/prescription \
  --header 'Accept: application/json' \
  --header 'Authorization: Basic a2VlcnRoaTprZWVydGhp' \
  --header 'Content-Type: application/json' \
  --data '{
	"patientId":"k123",
	"patientName":"keerthi",
	"drug":["mRNA","cov"]
}'
