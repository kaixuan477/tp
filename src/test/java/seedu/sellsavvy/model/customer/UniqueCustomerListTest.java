package seedu.sellsavvy.model.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.PersonCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.PersonCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalCustomers.ALICE;
import static seedu.sellsavvy.testutil.TypicalCustomers.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.customer.exceptions.DuplicateCustomerException;
import seedu.sellsavvy.model.customer.exceptions.CustomerNotFoundException;
import seedu.sellsavvy.testutil.CustomerBuilder;

public class UniqueCustomerListTest {

    private final UniqueCustomerList uniqueCustomerList = new UniqueCustomerList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueCustomerList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCustomerList.add(ALICE);
        assertTrue(uniqueCustomerList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCustomerList.add(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCustomerList.contains(editedAlice));
    }

    @Test
    public void hasSimilarPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.hasSimilarPerson(null));
    }

    @Test
    public void hasSimilarPerson_similarPersonNotInList_returnsFalse() {
        assertFalse(uniqueCustomerList.hasSimilarPerson(ALICE));
    }

    @Test
    public void hasSimilarPerson_onlySamePersonInList_returnsFalse() {
        uniqueCustomerList.add(ALICE);
        assertFalse(uniqueCustomerList.hasSimilarPerson(ALICE));
    }

    @Test
    public void hasSimilarPerson_similarPersonInList_returnsTrue() {
        Customer aliceDuplicate = new CustomerBuilder().withName(ALICE.getName().fullName.toUpperCase()).build();
        uniqueCustomerList.add(ALICE);
        assertTrue(uniqueCustomerList.hasSimilarPerson(aliceDuplicate));
    }

    @Test
    public void hasSimilarPerson_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCustomerList.add(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCustomerList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCustomerList.add(ALICE);
        assertThrows(DuplicateCustomerException.class, () -> uniqueCustomerList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> uniqueCustomerList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.setPerson(ALICE, ALICE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(ALICE);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCustomerList.add(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCustomerList.setPerson(ALICE, editedAlice);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(editedAlice);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.setPerson(ALICE, BOB);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(BOB);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.add(BOB);
        assertThrows(DuplicateCustomerException.class, () -> uniqueCustomerList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> uniqueCustomerList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.remove(ALICE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPersons((UniqueCustomerList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueCustomerList.add(ALICE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(BOB);
        uniqueCustomerList.setPersons(expectedUniqueCustomerList);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setPersons((List<Customer>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueCustomerList.add(ALICE);
        List<Customer> customerList = Collections.singletonList(BOB);
        uniqueCustomerList.setPersons(customerList);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(BOB);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Customer> listWithDuplicateCustomers = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateCustomerException.class, () -> uniqueCustomerList.setPersons(listWithDuplicateCustomers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCustomerList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void findEquivalentPerson_modelContainsEquivalentPerson() {
        uniqueCustomerList.add(ALICE);
        UniqueCustomerList copyList = uniqueCustomerList.copyPersons();
        Customer aliceDup = copyList.findEquivalentPerson(ALICE);
        assertNotSame(aliceDup, ALICE);
        assertEquals(aliceDup, ALICE);
    }

    @Test
    public void findEquivalentPerson_modelDoesNotContainsEquivalentPerson() {
        uniqueCustomerList.add(ALICE);
        assertThrows(CustomerNotFoundException.class, () -> uniqueCustomerList.findEquivalentPerson(BOB));
    }

    @Test
    public void findEquivalentPerson_nullInput_throwsAssertionError() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.findEquivalentPerson(null));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueCustomerList.asUnmodifiableObservableList().toString(), uniqueCustomerList.toString());
    }

    @Test
    public void copyPersons() {
        UniqueCustomerList uniqueCustomerListCopy = uniqueCustomerList.copyPersons();
        assertEquals(uniqueCustomerList, uniqueCustomerListCopy);
        assertNotSame(uniqueCustomerList, uniqueCustomerListCopy);
    }
}
