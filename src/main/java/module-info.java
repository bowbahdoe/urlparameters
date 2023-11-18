import org.jspecify.annotations.NullMarked;

@NullMarked
module dev.mccue.urlparameters {
    requires static org.jspecify;

    requires com.uwyn.urlencoder;

    exports dev.mccue.urlparameters;
}