package restAssuredTest.Test;

import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import restAssuredTest.Base.BasePage;

import static restAssuredTest.Constants.Constants.*;

public class TrelloTest extends BasePage {

    protected String idList;
    protected String idCard1;
    protected String idCard2;
    protected String boardId;

    @BeforeClass
    public void createBoard() {
        setUp();
        Response response = post("OzanKarali","/boards");
        assertName(response,"OzanKarali");
        boardId = extractBodyId(response);
    }
    @AfterClass
    public void tearDown() {
        deleteItem("id",boardId,deleteBoard);
    }

    @Test(priority = 1)
    public void createList() {
        Response response = post("ozanListe",getLists,"idBoard",boardId);
        assertName(response,"ozanListe");
        idList = extractBodyId(response);
    }

    @Test(priority = 2)
    public void addCard() {
        Response response1 = post("ozanCard_1",createCard,"idList",idList);
        response1.then()
                .statusCode(200);
        idCard1 = extractBodyId(response1);

        Response response2 = post("ozanCard_2",createCard,"idList",idList);
        response2.then()
                .statusCode(200);
        idCard2 = extractBodyId(response2);
    }

    @Test(priority = 3)
    public void updateCard() {
        updateCards(idCard1);
    }

    @Test(priority = 4)
    public void deleteCard() {
        deleteItem("cardId",idCard1,updateCard);
        deleteItem("cardId",idCard2,updateCard);
    }
}