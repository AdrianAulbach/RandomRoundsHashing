RandomRoundsHashing
===================
The idea behind this hashing-fuction is to make the number of hashing-rounds random.


If a password is valid, it has to be hashed on average for (minimalRounds + maximalRounds)/2 rounds. If it isn't valid, it has to be hashed for the full maximalRounds rounds to see that it is not valid.

If somebody tries to break a hash with a bruteforce-attack, he has a lot of wrong guesses, for which he has to do the maximal number of hashing-rounds, 
but in everyday use for a login-system you only have to do (minimalRounds + maximalRounds)/2 rounds on average for correct passwords (which means on average less waiting for the user)

So, long story short: The bad guy has up to twice as much work to do (per password guess) as you do on average

Licence
=======
I can't control it anyway, so:
WTFPL (http://www.wtfpl.net/txt/copying/)

But I'd appreciate a feedback if you're using (it's always nice to know something isn't completely useless) or even improving it: adrian.aulbach+github@gmail.com
