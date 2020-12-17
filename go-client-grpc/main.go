package main

import (
	"context"
	"log"
	"time"
  "fmt"

	//t "github.com/matheusfrancisco/bank/pkg/proto/translation"
	s "github.com/matheusfrancisco/bank/pkg/proto/scraper"
	"google.golang.org/grpc"
	"google.golang.org/grpc/metadata"
)

const (
	timestampFormat = time.StampNano // "Jan _2 15:04:05.000"
	streamingCount  = 10
)

func main() {
	log.Println("Client running ...")

  conn, err := grpc.Dial("localhost:8080", grpc.WithInsecure(), grpc.WithBlock())

	if err != nil {
		log.Fatalln(err)
	}
	defer conn.Close()
	c := s.NewScraperClient(conn)

	md, ok := metadata.FromOutgoingContext(context.Background())

	if !ok {
		md = metadata.New(nil)
	} else {
		md = md.Copy()
	}
	// Create metadata and context.
	//md := metadata.Pairs("content-type", "application/grpc +proto")
  //md = metadata.New(map[string]string{"token": "12312312"})

	ctx := metadata.NewOutgoingContext(context.Background(), md)

	ctx, cancel := context.WithTimeout(ctx, time.Second)
	defer cancel()
  request := &s.AuthRequest{AccountType: "PJ", AccountBranch: "1234", AccountNumber: "123456", Oper: "1234567", Password: "1231234"}

	r, err := c.Authenticate(ctx, request)

	if err != nil {
		log.Fatalf("failed to call Translate: %v", err)
	}
  fmt.Println(r)
}
