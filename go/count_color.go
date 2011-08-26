package main

import (
    "fmt"
    "io"
    "strings"
    "image/png" : "png"
    "strconv"
)

func CountColor(png io.Reader) int {
    img, _ := decoder.Decode(png)
    b := img.Bounds()
    m := map[string] int {}
    for x := b.Min.X; x < b.Max.X; x++ {
        for y := b.Min.Y; y < b.Max.Y; y++ {
            r,g,b,_ := img.At(x, y).RGBA()
            key := conv.Uitoa(uint(r)) + ":" + conv.Uitoa(uint(g)) + ":" + conv.Uitoa(uint(b))
            m[key] = 0
        }
    }
    return len(m)
}
