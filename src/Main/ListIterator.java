
package Main;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ListIterator implements Iterator {
        private ROB_NODE current;

        public ListIterator(ROB_NODE first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        
        public void remove()      { throw new UnsupportedOperationException();  }

        public ROB_NODE next() {
            if (!hasNext()) throw new NoSuchElementException();
            ROB_NODE item = current;
            current = current.next; 
            return item;
        }

    }