import org.example.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


class Test {
    @ParameterizedTest
    @ArgumentsSource(GetArguments.class)
    void testArgumentsSource(float a, float b, float ans) {
        Assertions.assertEquals(Main.del(a,b), ans);
    }


    static class GetArguments implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws SQLException {
            List<Arguments> result = new ArrayList<>();
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/del", "postgres", "mydb");

            Statement db = connection.createStatement();
            ResultSet rs = db.executeQuery("SELECT * FROM del");
            while (rs.next()){result.add(Arguments.of(rs.getFloat("a"),rs.getFloat("b"),rs.getFloat("ans")));}
            return result.stream();
        }
    }


}
