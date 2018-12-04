use std::io::{BufReader};
use std::io::prelude::*;
use std::fs::File;
use std::collections::HashMap;

fn main() {
    part1()
}

fn part1() {
    let f = File::open("../input/day2.txt").expect("");
    let f = BufReader::new(f);

    let twos = f.lines()
        .map( |l| l.unwrap())
        .filter(|s| {matches(2, s)})
        .count();


    let f = File::open("../input/day2.txt").expect("");
    let f = BufReader::new(f);
    let threes = f.lines()
        .map( |l| l.unwrap())
        .filter(|s| {matches(3, s)})
        .count();

    let result = twos * threes;

    println!("{}", result)
}

fn matches(n: usize, s: &String) -> bool {
    let mut letters: HashMap<char, usize> = HashMap::new();
    for ch in s.chars() {
        let counter = letters.entry(ch).or_insert(0);
        *counter += 1;
    }

    letters.iter().filter(|(_key, val)| {**val == n}).count() > 0
}