package laranja.comprovante.repository;

import laranja.comprovante.entity.Deposito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class DepositoRepository {

    private final DynamoDbTable<Deposito> table;

    public DepositoRepository(DynamoDbEnhancedClient enhancedClient,
                              @Value("${app.aws.dynamo-table-name}") String tableName){
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(Deposito.class));
    }

    public void save(Deposito deposito){
        this.table.putItem(deposito);
    }

}
