package com.pearlsea.sprinter.databinding;
import com.pearlsea.sprinter.R;
import com.pearlsea.sprinter.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentLoginBindingImpl extends FragmentLoginBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txtHelloWelcome, 1);
        sViewsWithIds.put(R.id.emailLabel, 2);
        sViewsWithIds.put(R.id.emailAddressTextBox, 3);
        sViewsWithIds.put(R.id.passwordLabel, 4);
        sViewsWithIds.put(R.id.passwordTextBox, 5);
        sViewsWithIds.put(R.id.frameStackellipseone, 6);
        sViewsWithIds.put(R.id.viewEllipseOne, 7);
        sViewsWithIds.put(R.id.txtSIGNUP, 8);
        sViewsWithIds.put(R.id.signupBackToWelcome, 9);
        sViewsWithIds.put(R.id.viewEllipseTwo, 10);
        sViewsWithIds.put(R.id.txtBACK, 11);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentLoginBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private FragmentLoginBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.FrameLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.TextView) bindings[4]
            , (android.widget.EditText) bindings[5]
            , (android.widget.FrameLayout) bindings[9]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[8]
            , (android.view.View) bindings[7]
            , (android.view.View) bindings[10]
            );
        this.linearLoginPage.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}