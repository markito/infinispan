package org.infinispan.container.offheap;

import org.infinispan.commons.marshall.WrappedBytes;
import org.infinispan.container.entries.InternalCacheEntry;
import org.infinispan.metadata.Metadata;

/**
 * Factory that can create {@link InternalCacheEntry} objects that use off-heap heap memory.  These are stored by
 * a long to symbolize the memory address.
 * @author wburns
 * @since 9.0
 */
public interface OffHeapEntryFactory {
   /**
    * Creates an off heap entry using the provided key value and metadata
    * @param key the key to use
    * @param value the value to use
    * @param metadata the metadata to use
    * @return the address of where the entry was created
    */
   long create(WrappedBytes key, WrappedBytes value, Metadata metadata);

   /**
    * Returns how many bytes in memory this address location uses assuming it is an {@link InternalCacheEntry}
    * @param address the address of the entry
    * @return how many bytes this address was allocated as
    */
   long determineSize(long address);

   /**
    * Returns the address to the next linked pointer if there is one for this bucket or 0 if there isn't one
    * @param address the address of the entry
    * @return the next address entry for this bucket or 0
    */
   long getNextLinkedPointerAddress(long address);

   /**
    * Called to update the next pointer index when a collision occurs requiring a linked list within the entries
    * themselves
    * @param address the address of the entry to update
    * @param value the value of the linked node to set
    */
   void updateNextLinkedPointerAddress(long address, long value);

   /**
    * Returns the hashCode of the address.  This
    * @param address the address of the entry
    * @return the has code of the entry
    */
   int getHashCodeForAddress(long address);

   /**
    * Create an entry from the off heap pointer
    * @param address the address of the entry to read
    * @return the entry created on heap from off heap
    */
   InternalCacheEntry<WrappedBytes, WrappedBytes> fromMemory(long address);

   /**
    * Returns the key for the given address
    * @param address the address pointer to find the key of
    * @return the key of the given address pointer
    */
   WrappedBytes getKey(long address);

   /**
    * Returns whether the given key as bytes is the same key as the key stored in the entry for the given address.
    * @param address the address of the entry's key to check
    * @param wrappedBytes the key to check equality with
    * @return whether or not the keys are equal
    */
   boolean equalsKey(long address, WrappedBytes wrappedBytes);
}
