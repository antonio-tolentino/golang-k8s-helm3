package main

import (
	"encoding/json"
	"log"
	"math/rand"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

// Book struct (Model)
type Book struct {
	ID     string  `json:"id"`
	Isbn   string  `json:"isbn"`
	Title  string  `json:"title"`
	Author *Author `json:"author"`
}

// Author struct
type Author struct {
	Firstname string `json:"firstname"`
	Lastname  string `json:"lastname"`
}

// Status struct
type Status struct {
	Status string `json:"status"`
}

//Init books var as a slice Book Struct
var books []Book

// ContentType header
const ContentType = "Content-Type"

// ApplicationJSON header
const ApplicationJSON = "application/json"

// APIBooksID path
const APIBooksID = "/api/books/{id}"

//APIBooks path
const APIBooks = "/api/books"

//APIHealth path
const APIHealth = "/api/health"

// Show API Health
func health(w http.ResponseWriter, r *http.Request) {
	log.Println(r.Method, r.URL, r.Proto, r.Header)
	w.Header().Set(ContentType, ApplicationJSON)
	var status Status
	status.Status = "I am fine!"
	json.NewEncoder(w).Encode(status)
}

// Get all books
func getBooks(w http.ResponseWriter, r *http.Request) {
	log.Println(r.Method, r.URL, r.Proto, r.Header)
	w.Header().Set(ContentType, ApplicationJSON)
	json.NewEncoder(w).Encode(books)
}

// Get single book
func getBook(w http.ResponseWriter, r *http.Request) {
	log.Printf("%v %v %v %v", r.Method, r.URL, r.Proto, r.Header)
	w.Header().Set(ContentType, ApplicationJSON)

	params := mux.Vars(r) // Get params

	// loop through books and find with id
	for _, item := range books {
		if item.ID == params["id"] {
			json.NewEncoder(w).Encode(item)
			return
		}
	}
	json.NewEncoder(w).Encode(&Book{})
}

// Create new book
func createBook(w http.ResponseWriter, r *http.Request) {
	log.Printf(r.Method, r.URL, r.Proto, r.Header)
	w.Header().Set(ContentType, ApplicationJSON)
	var book Book
	_ = json.NewDecoder(r.Body).Decode(&book)
	book.ID = strconv.Itoa(rand.Intn(10000000)) // Mock ID
	books = append(books, book)
	json.NewEncoder(w).Encode(book)
}

// Update Book
func updateBook(w http.ResponseWriter, r *http.Request) {
	log.Printf(r.Method, r.URL, r.Proto, r.Header)
	w.Header().Set(ContentType, ApplicationJSON)
	params := mux.Vars(r) // Get params
	for index, item := range books {

		if item.ID == params["id"] {
			books = append(books[:index], books[index+1:]...)
			var book Book
			_ = json.NewDecoder(r.Body).Decode(&book)
			book.ID = params["id"]
			books = append(books, book)
			json.NewEncoder(w).Encode(book)
			return
		}
	}
	json.NewEncoder(w).Encode(books)

}

// Delete Book
func deleteBook(w http.ResponseWriter, r *http.Request) {
	log.Printf(r.Method, r.URL, r.Proto, r.Header)
	w.Header().Set(ContentType, "application/json")
	params := mux.Vars(r) // Get params
	for index, item := range books {
		if item.ID == params["id"] {
			books = append(books[:index], books[index+1:]...)
			break
		}
	}
	json.NewEncoder(w).Encode(books)
}

func mockData() {
	// Mock Data - @todo - implement DB
	books = append(books, Book{ID: "1", Isbn: "448743",
		Title:  "Book One",
		Author: &Author{Firstname: "Agatha", Lastname: "Black"}})
	books = append(books, Book{ID: "2", Isbn: "556798",
		Title:  "Book Two",
		Author: &Author{Firstname: "Steve", Lastname: "White"}})
}

func createRouter() (router *mux.Router) {

	// Init Router
	r := mux.NewRouter()

	// Route Handlers / Endpoints
	r.HandleFunc(APIHealth, health).Methods("GET")
	r.HandleFunc(APIBooks, getBooks).Methods("GET")
	r.HandleFunc(APIBooksID, getBook).Methods("GET")
	r.HandleFunc(APIBooks, createBook).Methods("POST")
	r.HandleFunc(APIBooksID, updateBook).Methods("PUT")
	r.HandleFunc(APIBooksID, deleteBook).Methods("DELETE")

	return r
}

func main() {
	// create mock data
	mockData()

	// Start server
	log.Println("Starting server at port :8080")
	log.Fatal(http.ListenAndServe(":8080", createRouter()))
}
