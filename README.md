# ItemDuperMaybe

This script trades inventory of stuff between accounts and logs out the receiving account after each trade, endlessly, in the hopes that Jagex's servers will naturally crash and incur a rollback and duplicate the inventory of items being traded. 

Input names (RSN) of accounts to trade between in a list, and keep the order of names the list between all scripts, and place all accounts in same world and same location with all empty inventories except the inventory to be traded. The accs will trade each other in sequence until the 1st acc has logged and traded at least once, then, once items are passed back to 1st acc, it waits for a timer to start trading again. Accs must stay logged in after trading in case the servers crash for the last saved profile on the accs to be last logout.
