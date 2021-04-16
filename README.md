# num-id
pure digital id generate

register:
curl -X POST \
  http://localhost:8888/register \
  -H 'Content-Type: application/json' \
  -d '{
	"code":"tempMeetingRoom",
	"minLength":8,
	"maxLength":8,
	"cacheLength":1000
}'


getId:
curl -X GET \
  'http://localhost:8888/getOne?registerId=607901df1d94e76e1c8f72cf' 