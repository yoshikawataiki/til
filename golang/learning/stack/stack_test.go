package main

import (
	"testing"
)

func TestStack(t *testing.T) {
	s := Stack{}
	if s.Pop() != "" {
		t.Errorf("nil expected!")
	}
	s.Push("A")
	if s.Pop() != "A" {
		t.Errorf("A expected!")
	}
}
