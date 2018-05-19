# ATM-Cash-Dispenser
An ATM-Cash Dispenser program written in Java, that optimises the denominations of cash, by trying to give at least two different kinds with minimum possible banknotes for a given withdrawal. 
For example: If the ATM consists of the following denominations;
2000 - 3
500 - 4
200 - 2
100 - 5
50 - 6
20 - 8
10 - 4
5 - 10
1 - 20
and the amount to withdraw is Rs.2000, then the ATM will dispense 500*3+200*2+100*1 = 2000, rather than directly give the 2000 rupees note, as there are enough notes of lower denomination, and the contingency of providing atleast two different denominations is also obeyed. However, if there aren't enough lower denominations, but enough higher denominations, then in that case, the 2000 rupees note will be dispensed.
