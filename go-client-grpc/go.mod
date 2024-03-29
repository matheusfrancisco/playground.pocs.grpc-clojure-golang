module github.com/matheusfrancisco/bank

go 1.14

require (
	github.com/golang/protobuf v1.4.3
	golang.org/x/net v0.0.0-20201216054612-986b41b23924 // indirect
	golang.org/x/sys v0.0.0-20201214210602-f9fddec55a1e // indirect
	golang.org/x/text v0.3.4 // indirect
	google.golang.org/genproto v0.0.0-20201214200347-8c77b98c765d // indirect
	google.golang.org/grpc v1.34.0
	google.golang.org/protobuf v1.25.0
)

replace  github.com/matheusfrancisco/bank/pkg/proto/translation => ./pkg/proto/translation
replace  github.com/matheusfrancisco/bank/pkg/proto/scraper => ./pkg/proto/scraper
