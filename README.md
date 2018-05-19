# ATM-Cash-Dispenser
An ATM-Cash Dispenser program written in Java, that optimises the denominations of cash, by trying to give at least two different kinds with minimum possible banknotes for a given withdrawal. 
For example: If the ATM consists of the following denominations; <br />
Rs.2000 - 3 <br />
Rs.500 - 4 <br />
Rs.200 - 2 <br />
Rs.100 - 5 <br />
Rs.50 - 6 <br />
Rs.20 - 8 <br />
Rs.10 - 4 <br />
Rs.5 - 10 <br />
Rs.1 - 20 <br />
and the amount to withdraw is Rs.2000, then the ATM will dispense: <br /> 500x3+ 200x2+ 100x1 = 2000 <br /> rather than directly give the 2000 rupees note, as there are enough notes of lower denomination, and the contingency of providing atleast two different denominations is also obeyed. However, if there aren't enough lower denominations, but enough higher denominations, then in that case, the 2000 rupees note will be dispensed.
