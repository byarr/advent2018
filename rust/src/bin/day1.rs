use std::io::{BufReader};
use std::io::prelude::*;
use std::fs::File;
use std::collections::HashSet;

fn main() {
    part2()
}


fn part1() {
    let f = File::open("../input/day1.txt").expect("");
    let f = BufReader::new(f);

    let sum: i32 = f.lines()
        .map( |l| l.unwrap())
        .map( |s| s.parse::<i32>().unwrap())
        .sum();
    println!("{}", sum);
}

fn part2() {
    let f = File::open("../input/day1.txt").expect("");
    let f = BufReader::new(f);
    let deltas: Vec<i32> = f.lines()
        .map( |l| l.unwrap())
        .map( |s| s.parse::<i32>().unwrap()).collect();

    let mut sum: i32 = 0;
    let mut seen: HashSet<i32> = HashSet::new();
    seen.insert(sum);
    for d in deltas.iter().cloned().cycle() {
        sum += d;
        if (!seen.insert(sum)) {
            println!("{}", sum);
            return;
        }
    }
}

