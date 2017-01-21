# Barray

The project of implementing barray data structure for The Hong Kong Polytechnic University, Data Structures course (COMP 2011).

Barray is a dynamic data structure for storing elements.
Basically, it is a 2D array where the main array is called "catalog" and subarrays are called "branches".
As the number of elements grows, new branches are added on demand.
Similarly, when a branch becomes empty, it is deleted (catalog cell references to null).

If k is the smallest integer such that n <= 2^2k (where n is the number of elements), then the size of catalog and each branch is 2^k.
If the number of elements becomes bigger than 2^2k, the barray is expanded.
Similarly, if the barray is too big (n <= 2^(2 * (k - 1)), it shrinks.

In most cases the overall overhead of a barray is less than the overhead of a traditional dynamic array (if the array is full, double the size).

[![Capture.png](https://s29.postimg.org/w08rg3hbb/Capture.png)](https://postimg.org/image/76z7ffyar/)